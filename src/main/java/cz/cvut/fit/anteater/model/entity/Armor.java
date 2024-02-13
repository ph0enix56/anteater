package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Armor extends SourceableEntity {

	private ArmorType type;

	private Integer baseArmorClass;

	private Integer strengthRequirement;

	private Boolean stealthDisadvantage;

	@Data
	public class AbilityBonus {
		private Ability ability;

		private Integer max;
	}

	private List<AbilityBonus> bonuses;

	private String description;	
}
