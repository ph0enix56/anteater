package cz.cvut.fit.anteater.dto.request;

import org.hibernate.validator.constraints.Range;

import cz.cvut.fit.anteater.enumeration.Ability;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbilityInput {

	@NotNull
	private Ability label;

	@NotNull
	@Range(min = 1, max = 20)
	private Integer score;

	@NotNull
	private Boolean upByOne;

	@NotNull
	private Boolean upByTwo;
}
