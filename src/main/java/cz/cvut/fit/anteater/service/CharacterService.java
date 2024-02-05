package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repository;

	public CharacterService(DndCharacterRepository repository) {
		this.repository = repository;
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

	//public CharacterInfo getCharacterInfo(String id) {
	//	DndCharacter c = repository.findById(id).orElseThrow();
	//	return CharacterInfo.builder()

	//}

	public CharacterStats getCharacterStats(String id) {
		DndCharacter c = repository.findById(id).orElseThrow();
		return CharacterStats.builder()
			.id(c.getId())
			.proficiency_bonus(getProficiencyBonus(c.getLevel()))
			.initiative(getAbilityModifier(c.getAbility_scores().get(Ability.dexterity)))
			.speed(c.getRace().getSpeed())
			.hit_dice(c.getDnd_class().getHitDice())
			.hit_points(getHitPoints(c.getDnd_class().getHitDice(), c.getAbility_scores().get(Ability.constitution), c.getLevel()))
			.armor_class(99)
			.build();
	}
}
