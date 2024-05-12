package cz.cvut.fit.anteater.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbilityOutput {
	private String label;
	private Integer score;
	private Boolean upByOne;
	private Boolean upByTwo;
	private Integer result;
	private Integer modifier;
	private String displayName;
}
