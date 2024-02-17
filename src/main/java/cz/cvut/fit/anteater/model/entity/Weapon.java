package cz.cvut.fit.anteater.model.entity;

import java.util.Set;

import cz.cvut.fit.anteater.enumeration.DamageType;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.value.Dice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Weapon extends SourceableEntity {

	private WeaponType type;

	private Integer range;

	private Set<WeaponProperty> properties;

	private Dice damage;

	private DamageType damageType;
}
