package cz.cvut.fit.anteater.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmorType {
	unarmored("Unarmored"),
	light("Light Armor"),
	medium("Medium Armor"),
	heavy("Heavy Armor"),
	shield("Shields");

	private String name;
}
