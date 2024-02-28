package cz.cvut.fit.anteater.model.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.dto.AbilityOutput;
import cz.cvut.fit.anteater.model.dto.AttackOutput;
import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.dto.ProficiencyList;
import cz.cvut.fit.anteater.model.dto.SkillOutput;
import cz.cvut.fit.anteater.model.dto.SourcableInfo;
import cz.cvut.fit.anteater.model.dto.SpellcastingOutput;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.SkillAbilities;
import cz.cvut.fit.anteater.model.value.SlotData;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class CharacterMapper {

	public SourcableInfo toSrcInfo(SourceableEntity src) {
		return new SourcableInfo(src.getId(), src.getName());
	}

	public CharacterInfo toInfo(DndCharacter c) {
		return CharacterInfo.builder()
			.characterName(c.getCharacterName())
			.playerName(c.getPlayerName())
			.cardPhotoUrl(c.getCardPhotoUrl())
			.sheetPhotoUrl(c.getSheetPhotoUrl())
			.dndClass(toSrcInfo(c.getDndClass()))
			.race(toSrcInfo(c.getRace()))
			.background(toSrcInfo(c.getBackground()))
			.level(c.getLevel())
			.subclass(c.getSubclass())
			.build();
	}

	@Data
	@AllArgsConstructor
	private class AbilityStats {
		private Integer score;
		private Integer mod;
	}

	public Map<Ability, AbilityStats> getAbilityStats(DndCharacter c) {
		Map<Ability, AbilityStats> result = new HashMap<>();
		for (var i : c.getAbilities().entrySet()) {
			Integer bonus = (i.getValue().getUpByOne() ? 1 : 0) + (i.getValue().getUpByTwo() ? 2 : 0);
			Integer finalScore = i.getValue().getScore() + bonus;
			result.put(i.getKey(), new AbilityStats(finalScore, getAbilityModifier(finalScore)));
		}
		return result;
	}

	public Integer getProficiencyBonus(Integer level) {
		return (level - 1) / 4 + 2;
	}

	public Integer getAbilityModifier(Integer abilityScore) {
		return (abilityScore - 10) / 2;
	}

	public Integer getSkillModifier(Integer abilityModifier, Boolean proficient, Integer level) {
		return proficient ? abilityModifier + getProficiencyBonus(level) : abilityModifier;
	}

	public Integer getHitPoints(Dice hitDice, Integer conModifier, Integer level) {
		Integer initialHP = hitDice.getSides() + conModifier;
		Integer perLevelHP = hitDice.getSides() / 2 + 1 + conModifier;
		return initialHP + perLevelHP * (level - 1);
	}

	public Integer getArmorClass(Armor armor, Map<Ability, AbilityStats> abilities) {
		Integer result = armor.getBaseArmorClass();
		for (var i : armor.getBonuses()) {
			result += Math.min(i.getMax(), abilities.get(i.getAbility()).mod);
		}
		return result;
	}

	public CharacterStats toStats(DndCharacter c) {
		var abilities = getAbilityStats(c);
		return CharacterStats.builder()
		.proficiencyBonus(getProficiencyBonus(c.getLevel()))
		.initiative(abilities.get(Ability.dexterity).getMod())
		.speed(c.getRace().getSpeed())
			.hitDice(c.getDndClass().getHitDice())
			.hitPoints(getHitPoints(c.getDndClass().getHitDice(), abilities.get(Ability.constitution).getMod(), c.getLevel()))
			.armorClass(getArmorClass(c.getArmor(), abilities))
			.build();
		}

	public List<AbilityOutput> toAbilityOutput(DndCharacter c) {
		List<AbilityOutput> result = new ArrayList<>();
		var stats = getAbilityStats(c);
		for (var i : c.getAbilities().entrySet()) {
			result.add(new AbilityOutput(
				i.getKey().toString(),
				i.getValue().getScore(),
				i.getValue().getUpByOne(),
				i.getValue().getUpByTwo(),
				stats.get(i.getKey()).getScore(),
				stats.get(i.getKey()).getMod(),
				i.getKey().getName()));
		}
		return result;
	}

	public List<SkillOutput> toSkills(DndCharacter c) {
		List<SkillOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			Ability ab = SkillAbilities.SKILL_TO_ABILITY_MAP.get(sk);
			result.add(
				new SkillOutput(
					sk.toString(),
					ab.toString(),
					getSkillModifier(getAbilityStats(c).get(ab).mod, c.getSkills().contains(sk), c.getLevel()),
					c.getSkills().contains(sk),
					sk.getName() + " (" + ab.getAbbreviation() + ")"));
		}
		return result;
	}

	public List<SkillOutput> toSavingThrows(DndCharacter c) {
		List<SkillOutput> result = new ArrayList<>();
		Set<Ability> saves = c.getDndClass().getSavingThrowProficiencies().getDefaults();
		for (Ability ab : Ability.values()) {
			result.add(
				new SkillOutput(
					ab.toString(),
					ab.toString(),
					getSkillModifier(getAbilityStats(c).get(ab).mod, saves.contains(ab), c.getLevel()),
					saves.contains(ab),
					ab.getName()));
		}
		return result;
	}

	public List<AttackOutput> toAttacks(DndCharacter c) {
		List<AttackOutput> result = new ArrayList<>();
		for (var i : c.getWeapons()) {
			Boolean proficient = c.getDndClass().getWeaponProficiencies().contains(i.getType())
				|| c.getDndClass().getWeaponProficiencyIds().contains(i.getId());
			Integer strMod = getAbilityStats(c).get(Ability.strength).mod;
			Integer dexMod = getAbilityStats(c).get(Ability.dexterity).mod;
			Integer attackMod = 0;
			if (i.getProperties().contains(WeaponProperty.finesse)) attackMod = Math.max(strMod, dexMod);
			else if (i.getRanged() == true) attackMod = dexMod;
			else attackMod = strMod;
			Integer attackBonus = getSkillModifier(attackMod, proficient, c.getLevel());
			String damage = i.getDamage().getNotation() + " + " + attackMod + " " + i.getDamageType();
			result.add(new AttackOutput(i.getId(), i.getName(), attackBonus, damage));
		}
		return result;
	}

	public SpellcastingOutput toSpellcastingOutput(DndCharacter c) {
		if (c.getDndClass().getSpellcasting() == null) return null;

		var abilities = getAbilityStats(c);
		Ability spellAbility = c.getDndClass().getSpellcasting().getAbility();
		Integer modifier = abilities.get(spellAbility).mod;
		Integer saveDc = 8 + modifier + getProficiencyBonus(c.getLevel());
		List<SlotData> slotsRes = new ArrayList<>();
		List<Integer> slots = c.getDndClass().getSpellcasting().getSlots().get(c.getLevel());
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i) > 0) slotsRes.add(new SlotData(i + 1, slots.get(i)));
		}
		return SpellcastingOutput.builder()
			.ability(spellAbility)
			.modifier(modifier)
			.saveDc(saveDc)
			.slots(slotsRes)
			.spells(c.getSpells())
			.build();
	}

	// TODO: add individual weapon and armor proficiencies, not just the types
	public ProficiencyList toProficiencies(DndCharacter c) {
		List<String> armor = new ArrayList<>();
		c.getDndClass().getArmorProficiencies().stream().map(ArmorType::getName).forEach(armor::add);

		List<String> weapons = new ArrayList<>();
		c.getDndClass().getWeaponProficiencies().stream().map(WeaponType::getName).forEach(weapons::add);

		List<String> tools = c.getTools().stream().map(i -> i.getItem().getName()).toList();
		List<String> languages = c.getLanguages().stream().map(i -> i.getItem().getName()).toList();
		return ProficiencyList.builder()
			.armor(armor)
			.weapons(weapons)
			.tools(tools)
			.languages(languages)
			.build();
	}

	public List<TextFeature> toFeatures(DndCharacter c, Boolean allLevels) {
		List<TextFeature> features = new ArrayList<>();
		features.addAll(c.getBackground().getFeatures());
		features.addAll(c.getRace().getFeatures());
		features.addAll(c.getDndClass().getFeatures());
		if (!allLevels) features.removeIf(f -> f.getLevelMinimum() > c.getLevel());
		return features;
	}

	public CharacterComplete toComplete(DndCharacter c) {
		return CharacterComplete.builder()
			.id(c.getId())
			.info(toInfo(c))
			.stats(toStats(c))
			.abilities(toAbilityOutput(c))
			.skills(toSkills(c))
			.savingThrows(toSavingThrows(c))
			.armor(c.getArmor())
			.attacks(toAttacks(c))
			.spellcasting(toSpellcastingOutput(c))
			.proficiencies(toProficiencies(c))
			.tools(c.getTools())
			.languages(c.getLanguages())
			.features(toFeatures(c, false))
			.build();
	}
}
