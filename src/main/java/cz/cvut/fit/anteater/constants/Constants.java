package cz.cvut.fit.anteater.constants;

import java.io.File;
import java.util.List;
import java.util.Map;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Armor;

public class Constants {
	private Constants() {}

	/** Base URL of the API. */
	public static final String BASE_API_URL = "api/";

	/** URL of the frontend for allowing CORS. */
	public static final String FRONTEND_URL = "http://localhost:3000";

	/** PDF file with correspoding forms that will be used for PDF export. */
	public static final File PDF_TEMPLATE_FILE = new File("src/main/resources/anteater_character_sheet_v1.pdf");

	/** Number of attacks to be exported to the PDF. */
	public static final Integer PDF_ATTACK_COUNT = 3;

	/** Path to directory where PDF files will be exported. */
	public static final String PDF_EXPORT_DIRECTORY = "output/";

	/** List of abilities in the order they are traditionally presented in the character sheet. */
	public static final List<Ability> ABILITY_ORDER = List.of(
		Ability.strength,
		Ability.dexterity,
		Ability.constitution,
		Ability.intelligence,
		Ability.wisdom,
		Ability.charisma
	);

	/** Map of skills to the ability they are based on. */
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

	/** Speed penalty (in feet) to be applied when a character wears armor and doesn't meet its strength requirement. */
	public static final Integer ARMOR_SPEED_PENALTY = 10;

	/** Armor instance to be used when a character does not have any other armor
	 * equipped and is not using any other feature to calculate their AC. */
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
