package cz.cvut.fit.anteater.model.dto;

import org.hibernate.validator.constraints.Range;

import cz.cvut.fit.anteater.enumeration.Ability;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbilityInput {

	private Ability label;

	@Range(min = 1, max = 20)
	private Integer score;

	private Boolean upByOne;

	private Boolean upByTwo;
}
