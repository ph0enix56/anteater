package cz.cvut.fit.anteater.dto.pdf;

import lombok.Value;

@Value
public class SkillPdfOutput {
	private String abbreviation;
	private Integer modifier;
	private Boolean proficient;
}
