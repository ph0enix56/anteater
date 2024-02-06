package cz.cvut.fit.anteater.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillStats {
	private Integer modifier;
	private Boolean proficient;
}
