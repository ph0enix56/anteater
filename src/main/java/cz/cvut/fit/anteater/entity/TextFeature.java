package cz.cvut.fit.anteater.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextFeature {
	private String title;
	private String text;

	public TextFeature(String title, String text) {
		this.title = title;
		this.text = text;
	}
}
