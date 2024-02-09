package cz.cvut.fit.anteater.model.value;

import java.util.Map;
import java.util.HashMap;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;

public class SkillAbilities {
	public static final Map<Skill, Ability> SKILL_TO_ABILITY_MAP = new HashMap<>() {{
		put(Skill.acrobatics, Ability.dexterity);
		put(Skill.animal_handling, Ability.wisdom);
		put(Skill.arcana, Ability.intelligence);
		put(Skill.athletics, Ability.strength);
		put(Skill.deception, Ability.charisma);
		put(Skill.history, Ability.intelligence);
		put(Skill.insight, Ability.wisdom);
		put(Skill.intimidation, Ability.charisma);
		put(Skill.investigation, Ability.intelligence);
		put(Skill.medicine, Ability.wisdom);
		put(Skill.nature, Ability.intelligence);
		put(Skill.perception, Ability.wisdom);
		put(Skill.performance, Ability.charisma);
		put(Skill.persuasion, Ability.charisma);
		put(Skill.religion, Ability.intelligence);
		put(Skill.sleight_of_hand, Ability.dexterity);
		put(Skill.stealth, Ability.dexterity);
		put(Skill.survival, Ability.wisdom);
	}};
}
