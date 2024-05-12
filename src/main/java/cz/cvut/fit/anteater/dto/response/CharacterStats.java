package cz.cvut.fit.anteater.dto.response;

import cz.cvut.fit.anteater.model.value.Dice;
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
}
