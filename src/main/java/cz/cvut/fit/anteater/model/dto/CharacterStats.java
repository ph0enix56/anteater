package cz.cvut.fit.anteater.model.dto;

import java.util.Map;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.Dice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterStats {
	private Integer speed;

	private Integer initiative;

	private Integer proficiency_bonus;

	private Dice hit_dice;

	private Integer hit_points;

	private Integer armor_class;

	private Map<Ability, Integer> ability_scores;

	private Map<Skill, SkillStats> skills;

	private Map<Ability, SkillStats> saving_throws;
}
