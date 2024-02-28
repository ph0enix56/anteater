package cz.cvut.fit.anteater.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeaponProperty {
	ammunition("Ammunition"),
	finesse("Finesse"),
	heavy("Heavy"),
	light("Light"),
	loading("Loading"),
	range("Range"),
	reach("Reach"),
	special("Special"),
	thrown("Thrown"),
	two_handed("Two-Handed"),
	versatile("Versatile");

	private String name;
	@JsonValue public String getName() { return name; }
}
