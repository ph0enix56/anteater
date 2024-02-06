package cz.cvut.fit.anteater.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.dto.SkillStats;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.SkillAbilities;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repository;

	public CharacterService(DndCharacterRepository repository) {
		this.repository = repository;
	}

	public List<CharacterInfo> getCharacterInfos() {
		return repository.findAllCharacterInfosBy();
	}

	public CharacterInfo getCharacterInfo(String id) {
		return repository.findCharacterInfoById(id).orElse(null);
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
		DndCharacter c = repository.findById(id).orElseThrow();
		var builder = CharacterStats.builder()
			.id(c.getId())
			.proficiency_bonus(getProficiencyBonus(c.getLevel()))
			.initiative(getAbilityModifier(c.getAbilities().get(Ability.dexterity)))
			.speed(c.getRace().getSpeed())
			.hit_dice(c.getDndClass().getHitDice())
			.hit_points(getHitPoints(c.getDndClass().getHitDice(), c.getAbilities().get(Ability.constitution), c.getLevel()))
			.armor_class(99)
			.ability_scores(c.getAbilities());

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
		builder.saving_throws(saves);
		return builder.build();
	}
}
