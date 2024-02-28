package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ability {
	strength("Strength"),
	dexterity("Dexterity"),
	constitution("Constitution"),
	intelligence("Intelligence"),
	wisdom("Wisdom"),
	charisma("Charisma");

	private String name;

	public String getAbbreviation() { return name.substring(0, 3); }
}
