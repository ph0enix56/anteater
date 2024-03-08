package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Armor extends SourceableEntity {

	@Value
	public static class AbilityBonus {

		private Ability ability;

		private Integer max;
	}

	private ArmorType type;

	private Integer baseArmorClass;

	private Integer strengthRequirement;

	private Boolean stealthDisadvantage;

	private List<AbilityBonus> bonuses;

	private String description;

	public Armor(String id, String name, Source source, ArmorType type, Integer baseArmorClass,
			Integer strengthRequirement, Boolean stealthDisadvantage, List<AbilityBonus> bonuses, String description) {
		super(id, name, source);
		this.type = type;
		this.baseArmorClass = baseArmorClass;
		this.strengthRequirement = strengthRequirement;
		this.stealthDisadvantage = stealthDisadvantage;
		this.bonuses = bonuses;
		this.description = description;
	}
}
