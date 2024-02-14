package cz.cvut.fit.anteater.model.value;

import cz.cvut.fit.anteater.enumeration.Ability;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreBonus {
	private Ability ability;
	private Integer bonus;
}
