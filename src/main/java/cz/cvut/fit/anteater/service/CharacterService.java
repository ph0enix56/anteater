package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.dto.SkillStats;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.SkillAbilities;
import cz.cvut.fit.anteater.model.value.TextFeature;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repo;
	private DndClassRepository classRepo;
	private RaceRepository raceRepo;
	private BackgroundRepository backgroundRepo;

	public CharacterService(DndCharacterRepository repository, DndClassRepository classRepository, RaceRepository raceRepository, BackgroundRepository backgroundRepository) {
		this.repo = repository;
		this.classRepo = classRepository;
		this.raceRepo = raceRepository;
		this.backgroundRepo = backgroundRepository;
	}

	public List<CharacterInfo> getCharacterInfos() {
		return repo.findAllCharacterInfosBy();
	}

	public CharacterInfo getCharacterInfo(String id) {
		return repo.findCharacterInfoById(id).orElse(null);
	}

	public Integer getAbilityModifier(Integer abilityScore) {
		return (abilityScore - 10) / 2;
	}

	public Integer getProficiencyBonus(Integer level) {
		return (level - 1) / 4 + 2;
	}

	public Integer getSkillModifier(Integer abilityModifier, Boolean proficient, Integer level) {
		return proficient ? abilityModifier + getProficiencyBonus(level) : abilityModifier;
	}

	public Integer getHitPoints(Dice hitDice, Integer conScore, Integer level) {
		Integer initialHP = hitDice.getSides() + getAbilityModifier(conScore);
		Integer perLevelHP = hitDice.getSides() / 2 + 1 + getAbilityModifier(conScore);
		return initialHP + perLevelHP * (level - 1);
	}

	public Integer getSkillModifier(Skill skill, Map<Ability, Integer> abilityScores, Boolean proficient, Integer level) {
		Ability ability = SkillAbilities.SKILL_TO_ABILITY_MAP.get(skill);
		Integer modifier = getAbilityModifier(abilityScores.get(ability));
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public Integer getSaveModifier(Ability ability, Map<Ability, Integer> abilityScores, Boolean proficient, Integer level) {
		Integer modifier = getAbilityModifier(abilityScores.get(ability));
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public CharacterStats getCharacterStats(String id) {
		DndCharacter c = repo.findById(id).orElseThrow();
		var builder = CharacterStats.builder()
			.proficiencyBonus(getProficiencyBonus(c.getLevel()))
			.initiative(getAbilityModifier(c.getAbilities().get(Ability.dexterity)))
			.speed(c.getRace().getSpeed())
			.hitDice(c.getDndClass().getHitDice())
			.hitPoints(getHitPoints(c.getDndClass().getHitDice(), c.getAbilities().get(Ability.constitution), c.getLevel()))
			.armorClass(99)
			.abilityScores(c.getAbilities());

		Map<Skill, SkillStats> skills = new TreeMap<>();
		for (Skill sk : Skill.values()) {
			skills.put(sk,
				new SkillStats(
					getSkillModifier(sk, c.getAbilities(), c.getSkills().contains(sk), c.getLevel()),
					c.getSkills().contains(sk)));
		}

		Map<Ability, SkillStats> saves = new TreeMap<>();
		for (Ability ab : Ability.values()) {
			saves.put(ab, new SkillStats(
				getSaveModifier(ab, c.getAbilities(), c.getSaves().contains(ab), c.getLevel()),
				c.getSaves().contains(ab)));
		}

		builder.skills(skills);
		builder.savingThrows(saves);
		builder.tools(c.getTools());
		builder.languages(c.getLanguages());
		return builder.build();
	}

	public List<TextFeature> getCharacterFeatures(String id, Boolean allLevels) {
		DndCharacter c = repo.findById(id).orElseThrow();
		List<TextFeature> features = new ArrayList<>();
		features.addAll(c.getBackground().getFeatures());
		features.addAll(c.getRace().getFeatures());
		features.addAll(c.getDndClass().getFeatures());
		if (!allLevels) features.removeIf(f -> f.getLevelMinimum() > c.getLevel());
		return features;
	}

	public DndCharacter saveCharacter(CharacterInput in, Boolean isUpdate) {
		if (in == null) throw new IllegalArgumentException("Entity cannot be null");
		DndCharacter c = DndCharacter.builder()
			.characterName(in.getCharacterName())
			.playerName(in.getPlayerName())
			.cardPhotoUrl(in.getCardPhotoUrl())
			.sheetPhotoUrl(in.getSheetPhotoUrl())
			.level(in.getLevel())
			.abilities(in.getAbilityScores())
			.skills(in.getSkills())
			.saves(in.getSavingThrows())
			.dndClass(classRepo.findById(in.getDndClass()).orElseThrow(() -> new IllegalArgumentException("Invalid class id")))
			.race(raceRepo.findById(in.getRace()).orElseThrow(() -> new IllegalArgumentException("Invalid race id")))
			.background(backgroundRepo.findById(in.getBackground()).orElseThrow(() -> new IllegalArgumentException("Invalid background id")))
			.build();
		if (isUpdate) {
			if (repo.existsById(in.getId()) == false) throw new IllegalArgumentException("Invalid update: entity does not exist"); 
			c.setId(in.getId());
		}
		return repo.save(c);
	}

	public void deleteCharacter(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (repo.existsById(id) == false) throw new NoSuchElementException("Entity with given ID not found");
		repo.deleteById(id);
	}
}
