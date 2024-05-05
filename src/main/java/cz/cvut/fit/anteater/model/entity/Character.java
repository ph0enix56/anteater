package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.constants.Constants;
import cz.cvut.fit.anteater.dto.request.AbilityInput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.Proficiency;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Document(collection = "character")
public class Character {

	@Id
	private String id;

	private String characterName;

	private String playerName;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;

	private List<Source> sources;

	@Field("classId")
	@DocumentReference
	private DndClass dndClass;

	private String subclass;

	@Field("raceId")
	@DocumentReference
	private Race race;

	private Size size;

	@Field("backgroundId")
	@DocumentReference
	private Background background;

	private Integer level;

	private Map<Ability, AbilityInput> abilities;

	private Set<Skill> skills;

	private List<Proficiency<Tool>> tools;

	private List<Proficiency<Language>> languages;

	private Armor armor;

	private List<Weapon> weapons;

	private List<Spell> spells;

	public Armor getArmor() {
		if (armor != null) return armor;
		if (dndClass != null && dndClass.getDefaultArmor() != null) return dndClass.getDefaultArmor();
		return Constants.NO_ARMOR_DEFAULT;
	}

	public Integer getAbilityScore(Ability ability) {
		AbilityInput val = abilities.get(ability);
		Integer bonus = (val.getUpByOne() ? 1 : 0) + (val.getUpByTwo() ? 2 : 0);
		return val.getScore() + bonus;
	}

	public Integer getAbilityModifier(Ability ability) {
		return (getAbilityScore(ability) - 10) / 2;
	}

	public Integer getHitPoints() {
		Integer conModifier = getAbilityModifier(Ability.constitution);
		Dice hitDice = dndClass.getHitDice();
		Integer initialHP = hitDice.getSides() + conModifier;
		Integer perLevelHP = hitDice.getSides() / 2 + 1 + conModifier;
		return initialHP + perLevelHP * (level - 1);
	}

	public Integer getProficiencyBonus() {
		return (level - 1) / 4 + 2;
	}

	public Integer getSkillModifier(Skill skill) {
		Integer abilityModifier = getAbilityModifier(Constants.SKILL_TO_ABILITY_MAP.get(skill));
		Integer proficiencyBonus = skills.contains(skill) ? getProficiencyBonus() : 0;
		return abilityModifier + proficiencyBonus;
	}

	public Integer getSaveModifier(Ability ability) {
		Integer abilityModifier = getAbilityModifier(ability);
		Integer proficiencyBonus = dndClass.getSavingThrowProficiencies().contains(ability) ?
			getProficiencyBonus() : 0;
		return abilityModifier + proficiencyBonus;
	}

	public Integer getInitiative() {
		return getAbilityModifier(Ability.dexterity);
	}

	public Integer getSpeed() {
		Integer speed = race.getSpeed();
		if (getAbilityScore(Ability.strength) < getArmor().getStrengthRequirement())
			speed -= Constants.ARMOR_SPEED_PENALTY;
		return speed;
	}

	public Integer getArmorClass() {
		Integer ac = getArmor().getBaseArmorClass();
		for (var i : getArmor().getBonuses()) {
			ac += Math.min(getAbilityModifier(i.getAbility()), i.getMax());
		}
		return ac;
	}

	private Boolean isProficientWithWeapon(Weapon weapon) {
		return dndClass.getWeaponProficiencyTypes().contains(weapon.getType())
			|| dndClass.getWeaponProficiencies().contains(weapon);
	}

	private Integer getWeaponBaseModifier(Weapon weapon) {
		Integer strMod = getAbilityModifier(Ability.strength);
		Integer dexMod = getAbilityModifier(Ability.dexterity);
		if (weapon.getProperties().contains(WeaponProperty.finesse)) return Math.max(strMod, dexMod);
		if (weapon.getRanged()) return dexMod;
		return strMod;
	}

	public Integer getWeaponAttackBonus(Weapon weapon) {
		return getWeaponBaseModifier(weapon) +
			(isProficientWithWeapon(weapon) ? getProficiencyBonus() : 0);
	}

	public Integer getWeaponDamageModifier(Weapon weapon) {
		return getWeaponBaseModifier(weapon);
	}

	public Integer getSpellAttackModifier() {
		if (dndClass.getSpellcasting() == null) throw new IllegalStateException("Character cannot cast spells");
		return getAbilityModifier(dndClass.getSpellcasting().getAbility());
	}

	public Integer getSpellSaveDC() {
		if (dndClass.getSpellcasting() == null) throw new IllegalStateException("Character cannot cast spells");
		return 8 + getSpellAttackModifier() + getProficiencyBonus();
	}
}
