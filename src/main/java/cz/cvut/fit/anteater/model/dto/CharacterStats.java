package cz.cvut.fit.anteater.model.dto;

import cz.cvut.fit.anteater.model.value.Dice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterStats {
	private String id;

	private Integer speed;

	private Integer initiative;

	private Integer proficiency_bonus;

	private Dice hit_dice;

	private Integer hit_points;

	private Integer armor_class;
}
