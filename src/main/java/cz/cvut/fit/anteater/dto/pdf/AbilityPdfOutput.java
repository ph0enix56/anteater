package cz.cvut.fit.anteater.dto.pdf;

import lombok.Value;

@Value
public class AbilityPdfOutput {
	private String abbreviation;
	private Integer score;
	private Integer modifier;
}
