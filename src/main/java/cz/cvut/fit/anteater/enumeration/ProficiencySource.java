package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProficiencySource {
	dndClass("class"),
	race("race"),
	background("background");

	private String name;

	@JsonValue public String getName() { return name; }
}
