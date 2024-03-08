package cz.cvut.fit.anteater.model.constants;

import java.util.List;
import java.util.Map;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Armor;

public class Constants {
	private Constants() {}

	public static final List<Ability> ABILITY_ORDER = List.of(
		Ability.strength,
		Ability.dexterity,
		Ability.constitution,
		Ability.intelligence,
		Ability.wisdom,
		Ability.charisma
	);

	public static final Map<Skill, Ability> SKILL_TO_ABILITY_MAP = Map.ofEntries(
		Map.entry(Skill.acrobatics, Ability.dexterity),
		Map.entry(Skill.animal_handling, Ability.wisdom),
		Map.entry(Skill.arcana, Ability.intelligence),
		Map.entry(Skill.athletics, Ability.strength),
		Map.entry(Skill.deception, Ability.charisma),
		Map.entry(Skill.history, Ability.intelligence),
		Map.entry(Skill.insight, Ability.wisdom),
		Map.entry(Skill.intimidation, Ability.charisma),
		Map.entry(Skill.investigation, Ability.intelligence),
		Map.entry(Skill.medicine, Ability.wisdom),
		Map.entry(Skill.nature, Ability.intelligence),
		Map.entry(Skill.perception, Ability.wisdom),
		Map.entry(Skill.performance, Ability.charisma),
		Map.entry(Skill.persuasion, Ability.charisma),
		Map.entry(Skill.religion, Ability.intelligence),
		Map.entry(Skill.sleight_of_hand, Ability.dexterity),
		Map.entry(Skill.stealth, Ability.dexterity),
		Map.entry(Skill.survival, Ability.wisdom)
	);

	public static final Armor NO_ARMOR_DEFAULT = new Armor(
		null,
		"No armor",
		null,
		ArmorType.unarmored,
		10,
		0,
		false,
		List.of(new Armor.AbilityBonus(Ability.dexterity, 10)),
		"Without armor, your character's AC equals 10 + their Dexterity modifier."
	);
}
