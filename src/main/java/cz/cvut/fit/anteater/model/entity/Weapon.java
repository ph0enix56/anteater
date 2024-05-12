package cz.cvut.fit.anteater.model.entity;

import java.util.Set;

import cz.cvut.fit.anteater.enumeration.DamageType;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.value.Dice;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Weapon extends SourceableEntity {

	private WeaponType type;

	private Boolean ranged;

	private Integer range;

	private Set<WeaponProperty> properties;

	private Dice damage;

	private DamageType damageType;

	public Weapon(String id, String name, Source source, WeaponType type, Boolean ranged, Integer range, Set<WeaponProperty> properties, Dice damage, DamageType damageType) {
		super(id, name, source);
		this.type = type;
		this.ranged = ranged;
		this.range = range;
		this.properties = properties;
		this.damage = damage;
		this.damageType = damageType;
	}
}
