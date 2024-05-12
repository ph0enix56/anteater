package cz.cvut.fit.anteater.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cz.cvut.fit.anteater.constants.Constants;
import cz.cvut.fit.anteater.dto.request.AbilityInput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.DamageType;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.value.BonusSet;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.Spellcasting;

public class DndCharacterTests {

	private static DndCharacter mockCharacter;
	private static DndClass dndClass;
	private static DndClass classWithoutDefaultArmor;
	private static Race race;
	private static Background background;
	private static Armor armor;
	private static Weapon weapon;
	private static Armor classDefaultArmor;

	@BeforeAll
	public static void setup() {
		weapon = new Weapon("0", "Test Weapon", new Source("tst", "Test Source"), WeaponType.martial, 
			true, 8, Set.of(WeaponProperty.light), new Dice(1, 7), DamageType.piercing);
		armor = new Armor("1", "Powerful Armor", null, null, 70, 24,
			null, List.of(new Armor.AbilityBonus(Ability.dexterity, 12)), null);
		classDefaultArmor = new Armor("0", "Class Armor", null, null, 10, 6, 
		null, 
		List.of(new Armor.AbilityBonus(Ability.dexterity, 17), new Armor.AbilityBonus(Ability.constitution, 14)), null);
		dndClass = new DndClass("0", "Test Name", new Source("tst", "Test Source"), 
			"Test Description", new Dice(1, 4),new ArrayList<>(), new ArrayList<>(), 
			null, new HashSet<>(), null, new HashSet<>(), 
			new ArrayList<>(), classDefaultArmor, new HashSet<>(),new ArrayList<>(), null);
		classWithoutDefaultArmor = new DndClass("0", "Test Name", new Source("tst", "Test Source"), 
			"Test Description", new Dice(1, 10),new ArrayList<>(), new ArrayList<>(), 
			null, new HashSet<>(), null, new HashSet<>(), 
			new ArrayList<>(), null, new HashSet<>(),new ArrayList<>(), null);
		race = new Race("0", "Test Name", new Source("tst", "Test Source"), 
			"Test Description", new ArrayList<>(), 30, Set.of(Size.medium), 
			null, null, null, 
			null);
		background = new Background(
			"0",
			"Test Background",
			new Source("tst", "Test Source"),
			"This is a test background",
			new ArrayList<>(),
			new BonusSet<>(2, new HashSet<>()),
			new BonusSet<>(3, new HashSet<>()),
			new BonusSet<>(1, new HashSet<>())
		);

		mockCharacter = DndCharacter.builder()
			.id("0")
			.characterName("Test Character")
			.playerName("Test Player")
			.cardPhotoUrl("Test Card Photo URL")
			.sheetPhotoUrl("Test Sheet Photo URL")
			.sources(new ArrayList<>())
			.dndClass(dndClass)
			.subclass("Test Subclass")
			.race(race)
			.size(Size.medium)
			.background(background)
			.tools(new ArrayList<>())
			.languages(new ArrayList<>())
			.abilities((Map.of(
				Ability.strength, new AbilityInput(Ability.strength,11, false, true),
				Ability.dexterity, new AbilityInput(Ability.dexterity,13, true, false),
				Ability.constitution, new AbilityInput(Ability.constitution,20, false, true),
				Ability.intelligence, new AbilityInput(Ability.intelligence,17, true, false),
				Ability.wisdom, new AbilityInput(Ability.wisdom,10, false, true),
				Ability.charisma, new AbilityInput(Ability.charisma,1, false, true))
			))
			.level(1)
			.skills(Set.of( Skill.deception, Skill.history, Skill.insight))
			.armor(armor)
			.weapons(List.of(weapon))
			.spells(new ArrayList<>())
			.build();
	}

	@Test
	public void getArmorTest() {
		Armor returnedArmor = mockCharacter.getArmor();
		var armorClass = mockCharacter.getDndClass().getDefaultArmor();
		assertEquals(returnedArmor, armor);
		assert(!armor.equals(armorClass));
	}

	@Test
	public void getArmorByClassTest() {
		var testCharacter = mockCharacter.toBuilder().armor(null).dndClass(dndClass).build();
		Armor returnedArmor = testCharacter.getArmor();
		assertEquals(returnedArmor, classDefaultArmor);
	}

	@Test
	public void getNoArmorTest() {
		var testCharacter = mockCharacter.toBuilder().dndClass(classWithoutDefaultArmor).armor(null).build();
		Armor returnedArmor = testCharacter.getArmor();
		assertEquals(returnedArmor, Constants.NO_ARMOR_DEFAULT);
	}

	@Test 
	public void getAbilityScoreTest() {
		assertEquals(13, mockCharacter.getAbilityScore(Ability.strength));
		assertEquals(14, mockCharacter.getAbilityScore(Ability.dexterity));
		assertEquals(20, mockCharacter.getAbilityScore(Ability.constitution));
		assertEquals(18, mockCharacter.getAbilityScore(Ability.intelligence));
		assertEquals(12, mockCharacter.getAbilityScore(Ability.wisdom));
		assertEquals(3, mockCharacter.getAbilityScore(Ability.charisma));
	}

	@Test
	public void getAbilityModifierTest() {
		assertEquals(1, mockCharacter.getAbilityModifier(Ability.strength));
		assertEquals(2, mockCharacter.getAbilityModifier(Ability.dexterity));
		assertEquals(5, mockCharacter.getAbilityModifier(Ability.constitution));
		assertEquals(4, mockCharacter.getAbilityModifier(Ability.intelligence));
		assertEquals(1, mockCharacter.getAbilityModifier(Ability.wisdom));
		assertEquals(-3, mockCharacter.getAbilityModifier(Ability.charisma));
	}

	@Test
	public void getHitPointsTest() {
		assertEquals(9, mockCharacter.getHitPoints());
		var testCharacter = mockCharacter.toBuilder().dndClass(classWithoutDefaultArmor).build();
		assertEquals(15, testCharacter.getHitPoints());
		testCharacter = mockCharacter.toBuilder().level(5).build();
		assertEquals(9, mockCharacter.getHitPoints());
	}

	@Test
	public void getProficiencyBonusTest() {
		assertEquals(2, mockCharacter.getProficiencyBonus());
		var testCharacter = mockCharacter.toBuilder().level(5).build();
		assertEquals(3, testCharacter.getProficiencyBonus());
		testCharacter = mockCharacter.toBuilder().level(9).build();
		assertEquals(4, testCharacter.getProficiencyBonus());
		testCharacter = mockCharacter.toBuilder().level(13).build();
		assertEquals(5, testCharacter.getProficiencyBonus());
		testCharacter = mockCharacter.toBuilder().level(17).build();
		assertEquals(6, testCharacter.getProficiencyBonus());
		testCharacter = mockCharacter.toBuilder().level(20).build();
		assertEquals(6, testCharacter.getProficiencyBonus());
	}

	@Test
	public void getSkillModifierTest() {
		assertEquals(-1, mockCharacter.getSkillModifier(Skill.deception));
		assertEquals(6, mockCharacter.getSkillModifier(Skill.history));
		assertEquals(-3, mockCharacter.getSkillModifier(Skill.intimidation));
		assertEquals(1, mockCharacter.getSkillModifier(Skill.animal_handling));
	}

	@Test
	public void getSaveModifierTest() {
		assertEquals(1, mockCharacter.getSaveModifier(Ability.strength));
		assertEquals(2, mockCharacter.getSaveModifier(Ability.dexterity));
		assertEquals(5, mockCharacter.getSaveModifier(Ability.constitution));
		assertEquals(4, mockCharacter.getSaveModifier(Ability.intelligence));
		assertEquals(1, mockCharacter.getSaveModifier(Ability.wisdom));
		assertEquals(-3, mockCharacter.getSaveModifier(Ability.charisma));
	}

	@Test
	public void getInitiativeTest() {
		assertEquals(2, mockCharacter.getInitiative());
	}

	@Test
	public void getSpeedTest() {
		assertEquals(20, mockCharacter.getSpeed());
		var testCharacter = mockCharacter.toBuilder().armor(classDefaultArmor).build();
		assertEquals(30, testCharacter.getSpeed());
		testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.strength, new AbilityInput(Ability.strength, 1, false, true))).build();
		assertEquals(20, testCharacter.getSpeed());
	}

	@Test
	public void getArmorClassTest() {
		assertEquals(72, mockCharacter.getArmorClass());
		var testCharacter = mockCharacter.toBuilder().armor(classDefaultArmor).build();
		assertEquals(17, testCharacter.getArmorClass());
	}

	@Test
	public void getArmorClassNoArmorTest() {
		var testCharacter = mockCharacter.toBuilder().armor(null).build();
		assertEquals(17, testCharacter.getArmorClass());
	}
	
	@Test
	public void getWeaponAttackBonusTest() {
		assertEquals(2, mockCharacter.getWeaponAttackBonus(weapon));    
	}

	@Test
	public void getWeaponAttackBonusWithGreaterAbilitiesTest() {
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.dexterity, new AbilityInput(Ability.dexterity, 20, false, true),
			Ability.strength, new AbilityInput(Ability.strength, 20, false, true)
			)).build();
		assertEquals(5, testCharacter.getWeaponAttackBonus(weapon));
	}

	@Test
	public void getWeaponDamageModifierByDexTest() {
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.strength, new AbilityInput(Ability.strength, 10, false, true),
			Ability.dexterity, new AbilityInput(Ability.dexterity, 10, false, true)
			)).build();
		assertEquals(testCharacter.getAbilityModifier(Ability.dexterity), testCharacter.getWeaponDamageModifier(weapon));
	}

	@Test
	public void getWeaponDamageModifierWithFinnesseWeaponTest() {
		var testWeapon = new Weapon("1", "Test Weapon", new Source("Test Source", "1.0"), WeaponType.martial, 
			true, 8, Set.of(WeaponProperty.finesse, WeaponProperty.light), new Dice(1, 7), DamageType.piercing);
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.strength, new AbilityInput(Ability.strength, 14, false, true),
			Ability.dexterity, new AbilityInput(Ability.dexterity, 10, false, true)
			)).weapons(List.of(testWeapon)).build();
		assertEquals(testCharacter.getAbilityModifier(Ability.strength), testCharacter.getWeaponDamageModifier(testWeapon));
	}

	@Test
	public void getWeaponDamageByStrTest() {
		var weapon = new Weapon("1", "Test Weapon", new Source("Test Source", "1.0"), WeaponType.martial, 
			false, 8, Set.of(WeaponProperty.heavy), new Dice(1, 7), DamageType.piercing);
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.strength, new AbilityInput(Ability.strength, 2, false, true),
			Ability.dexterity, new AbilityInput(Ability.dexterity, 10, false, true)
			)).build();
		assertEquals(testCharacter.getAbilityModifier(Ability.strength), testCharacter.getWeaponDamageModifier(weapon));
	}

	@Test
	public void getSpellAttackWhenNoSpellcastingModifierTest() {
		assertThrowsExactly(IllegalStateException.class, () -> mockCharacter.getSpellAttackModifier());
	}

	@Test
	public void getSpellAttackWhenSpellcastingModifierTest(){
		var dClass = new DndClass("Test ID", "Test Name", new Source("tst", "Test Source"), 
			"Test Description", new Dice(1, 4),
			new ArrayList<>(), new ArrayList<>(), 
			null, new HashSet<>(), null, new HashSet<>(), 
			new ArrayList<>(), classDefaultArmor, new HashSet<>(),new ArrayList<>(), 
			new Spellcasting(
				Ability.intelligence, 
				List.of(
					List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
					List.of(1, 2, 3, 9, 5, 6, 7, 8, 9))));
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.intelligence, new AbilityInput(Ability.intelligence, 20, false, true),
			Ability.dexterity, new AbilityInput(Ability.dexterity, 10, false, true)))
			.dndClass(dClass)
			.build();
		assertEquals(5, testCharacter.getSpellAttackModifier());
	}

	@Test
	public void getSpellSaveDCWhenNoSpellcastingTest() {
		assertThrowsExactly(IllegalStateException.class, () -> mockCharacter.getSpellSaveDC());
	}

	@Test
	public void getSpellSaveDCWhenSpellcastingTest() {
		var dClass = new DndClass("Test ID", "Test Name", new Source("tst", "Test Source"), 
			"Test Description", new Dice(1, 4),
			new ArrayList<>(), new ArrayList<>(), 
			null, new HashSet<>(), null, new HashSet<>(), 
			new ArrayList<>(), classDefaultArmor, new HashSet<>(), new ArrayList<>(), 
			new Spellcasting(
				Ability.intelligence,
				List.of(
					List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
					List.of(1, 2, 3, 9, 5, 6, 7, 8, 9))));
		var testCharacter = mockCharacter.toBuilder().abilities(Map.of(
			Ability.intelligence, new AbilityInput(Ability.intelligence, 20, false, true),
			Ability.dexterity, new AbilityInput(Ability.dexterity, 10, false, true)))
			.dndClass(dClass)
			.build();
		assertEquals(15, testCharacter.getSpellSaveDC());
	}
}
