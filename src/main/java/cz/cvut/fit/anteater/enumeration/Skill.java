package cz.cvut.fit.anteater.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
}
