package cz.cvut.fit.anteater.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillOutput {
	private String label;
	private String ability;
	private Integer modifier;
	private Boolean proficient;
	private String displayName;
}
