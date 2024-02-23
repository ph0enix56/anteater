package cz.cvut.fit.anteater.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeaponType {
	martial("Martial Weapons"),
	simple("Simple Weapons");

	private String name;
}
