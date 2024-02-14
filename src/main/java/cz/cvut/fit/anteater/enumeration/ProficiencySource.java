package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProficiencySource {
	dndClass("class"),
	race("race"),
	background("background");

	private String name;

	ProficiencySource(String name) { this.name = name; }
	@JsonValue public String getName() { return name; }
}
