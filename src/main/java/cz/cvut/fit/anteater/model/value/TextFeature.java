package cz.cvut.fit.anteater.model.value;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class TextFeature {
	@NonNull private String title;
	@NonNull private String text;
	@NonNull private Integer levelMinimum;

	public TextFeature(String title, String text) {
		this(title, text, 1);
	}
}
