package cz.cvut.fit.anteater.model.value;

import lombok.Value;

@Value
public class TextFeature {

	private String title;

	private String text;

	private Integer levelMinimum;
}
