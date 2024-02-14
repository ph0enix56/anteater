package cz.cvut.fit.anteater.model.dto;

import java.util.List;
import java.util.Map;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.Proficiency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterStats {
	private Integer speed;

	private Integer initiative;

	private Integer proficiencyBonus;

	private Dice hitDice;

	private Integer hitPoints;

	private Integer armorClass;

	private Map<Ability, Integer> abilityScores;

	private Map<Skill, SkillStats> skills;

	private Map<Ability, SkillStats> savingThrows;

	private List<Proficiency<Tool>> tools;

	private List<Proficiency<Language>> languages;
}
