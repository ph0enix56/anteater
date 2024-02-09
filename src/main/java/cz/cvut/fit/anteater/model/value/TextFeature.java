package cz.cvut.fit.anteater.model.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextFeature {
	private String title;
	private String text;
	private Integer levelMinimum;

	public TextFeature(String title, String text) {
		this(title, text, 1);
	}
}
