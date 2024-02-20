package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Ability {
	strength("Strength", "Str"),
	dexterity("Dexterity", "Dex"),
	constitution("Constitution", "Con"),
	intelligence("Intelligence", "Int"),
	wisdom("Wisdom", "Wis"),
	charisma("Charisma", "Cha");

	private String name;
	private String abbreviation;

	Ability(String name, String abbreviation) { this.name = name; this.abbreviation = abbreviation; }

	@JsonValue
	public String getName() { return name; }

	public String getAbbreviation() { return abbreviation; }
}
