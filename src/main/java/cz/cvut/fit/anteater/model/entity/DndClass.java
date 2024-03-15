package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.Spellcasting;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@Document(collection = "class")
public class DndClass extends SourceableEntity {

	private String description;

	private Dice hitDice;

	private List<TextFeature> features;

	private List<String> subclasses;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("saves")
	private Set<Ability> savingThrowProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;

	@Field("armorTypes")
	private Set<ArmorType> armorProficiencyTypes;

	@Field("armor")
	private List<Armor> armorProficiencies;

	private Armor defaultArmor;

	@Field("weaponTypes")
	private Set<WeaponType> weaponProficiencyTypes;

	@Field("weapons")
	private List<Weapon> weaponProficiencies;

	private Spellcasting spellcasting;

	public DndClass(String id, String name, Source source, String description, Dice hitDice, List<TextFeature> features,
			List<String> subclasses, BonusList<Skill> skillProficiencies, Set<Ability> savingThrowProficiencies,
			BonusList<Tool> toolProficiencies, Set<ArmorType> armorProficiencyTypes, List<Armor> armorProficiencies,
			Armor defaultArmor, Set<WeaponType> weaponProficiencyTypes, List<Weapon> weaponProficiencies,
			Spellcasting spellcasting) {
		super(id, name, source);
		this.description = description;
		this.hitDice = hitDice;
		this.features = features;
		this.subclasses = subclasses;
		this.skillProficiencies = skillProficiencies;
		this.savingThrowProficiencies = savingThrowProficiencies;
		this.toolProficiencies = toolProficiencies;
		this.armorProficiencyTypes = armorProficiencyTypes;
		this.armorProficiencies = armorProficiencies;
		this.defaultArmor = defaultArmor;
		this.weaponProficiencyTypes = weaponProficiencyTypes;
		this.weaponProficiencies = weaponProficiencies;
		this.spellcasting = spellcasting;
	}
}
