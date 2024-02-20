package cz.cvut.fit.anteater.model.dto;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbilityInput {

	@Range(min = 1, max = 20)
	private Integer score;

	private Boolean upByOne;

	private Boolean upByTwo;
}
