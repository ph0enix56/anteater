package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Skill {
	acrobatics("Acrobatics"),
	animal_handling("Animal Handling"),
	arcana("Arcana"),
	athletics("Athletics"),
	deception("Deception"),
	history("History"),
	insight("Insight"),
	intimidation("Intimidation"),
	investigation("Investigation"),
	medicine("Medicine"),
	nature("Nature"),
	perception("Perception"),
	performance("Performance"),
	persuasion("Persuasion"),
	religion("Religion"),
	sleight_of_hand("Sleight of Hand"),
	stealth("Stealth"),
	survival("Survival");

	private String name;

	Skill(String name) { this.name = name; }
	@JsonValue public String getName() { return name; }
}
