package cz.cvut.fit.anteater.business.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.dto.pdf.AbilityPdfOutput;
import cz.cvut.fit.anteater.dto.pdf.CharacterPdfOutput;
import cz.cvut.fit.anteater.dto.pdf.SkillPdfOutput;
import cz.cvut.fit.anteater.dto.response.AbilityOutput;
import cz.cvut.fit.anteater.dto.response.AttackOutput;
import cz.cvut.fit.anteater.dto.response.CharacterComplete;
import cz.cvut.fit.anteater.dto.response.CharacterInfo;
import cz.cvut.fit.anteater.dto.response.CharacterShort;
import cz.cvut.fit.anteater.dto.response.CharacterStats;
import cz.cvut.fit.anteater.dto.response.ProficiencyList;
import cz.cvut.fit.anteater.dto.response.SkillOutput;
import cz.cvut.fit.anteater.dto.response.SourcableInfo;
import cz.cvut.fit.anteater.dto.response.SpellcastingOutput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ArmorType;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.WeaponProperty;
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.AllArgsConstructor;
import lombok.Value;

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
			.size(c.getSize().toString())
			.subclass(c.getSubclass())
			.build();
	}

	public CharacterShort toShort(DndCharacter c) {
		return new CharacterShort(c.getId(), toInfo(c));
	}

	@Value
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

	public Integer getSpeed(Integer baseSpeed, Armor armor, Integer strengthScore) {
		if (strengthScore < armor.getStrengthRequirement()) return baseSpeed - Constants.ARMOR_SPEED_PENALTY;
		return baseSpeed;
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
			.speed(getSpeed(c.getRace().getSpeed(), c.getArmor(), abilities.get(Ability.strength).score))
			.hitDice(new Dice(c.getLevel(), c.getDndClass().getHitDice().getSides()))
			.hitPoints(getHitPoints(c.getDndClass().getHitDice(), abilities.get(Ability.constitution).getMod(), c.getLevel()))
			.armorClass(getArmorClass(c.getArmor(), abilities))
			.build();
		}

	public List<AbilityOutput> toAbilitiesOutput(DndCharacter c) {
		List<AbilityOutput> result = new ArrayList<>();
		var stats = getAbilityStats(c);
		for (Ability ab : Constants.ABILITY_ORDER) {
			var input = c.getAbilities().get(ab);
			result.add(new AbilityOutput(
				ab.toString(),
				input.getScore(),
				input.getUpByOne(),
				input.getUpByTwo(),
				stats.get(ab).getScore(),
				stats.get(ab).getMod(),
				ab.getName()));
		}
		return result;
	}

	public List<SkillOutput> toSkills(DndCharacter c) {
		List<SkillOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			Ability ab = Constants.SKILL_TO_ABILITY_MAP.get(sk);
			result.add(new SkillOutput(
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
		Set<Ability> saves = c.getDndClass().getSavingThrowProficiencies();
		for (Ability ab : Ability.values()) {
			result.add(new SkillOutput(
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
			Boolean proficient = c.getDndClass().getWeaponProficiencyTypes().contains(i.getType())
				|| c.getDndClass().getWeaponProficiencies().contains(i);

			Integer strMod = getAbilityStats(c).get(Ability.strength).mod;
			Integer dexMod = getAbilityStats(c).get(Ability.dexterity).mod;
			Integer attackMod = 0;
			if (i.getProperties().contains(WeaponProperty.finesse)) attackMod = Math.max(strMod, dexMod);
			else if (i.getRanged() == true) attackMod = dexMod;
			else attackMod = strMod;
			Integer attackBonus = getSkillModifier(attackMod, proficient, c.getLevel());

			StringBuilder dmgBuilder = new StringBuilder(i.getDamage().getNotation());
			if (attackMod > 0) dmgBuilder.append(" + ").append(attackMod);
			else if (attackMod < 0) dmgBuilder.append(" - ").append(-attackMod);
			String damage = dmgBuilder.append(" ").append(i.getDamageType()).toString();
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
		List<SpellcastingOutput.SlotData> slotsRes = new ArrayList<>();
		List<Integer> slots = c.getDndClass().getSpellcasting().getSlotsByLevel(c.getLevel());
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i) > 0) slotsRes.add(new SpellcastingOutput.SlotData(i + 1, slots.get(i)));
		}
		return SpellcastingOutput.builder()
			.abilityAbbreviation(spellAbility.getAbbreviation())
			.modifier(modifier)
			.saveDc(saveDc)
			.slots(slotsRes)
			.spells(c.getSpells())
			.build();
	}

	public ProficiencyList toProficiencies(DndCharacter c) {
		List<String> armor = new ArrayList<>();
		c.getDndClass().getArmorProficiencyTypes().stream().map(ArmorType::getName).forEach(armor::add);
		c.getDndClass().getArmorProficiencies().stream().map(Armor::getName).forEach(armor::add);

		List<String> weapons = new ArrayList<>();
		c.getDndClass().getWeaponProficiencyTypes().stream().map(WeaponType::getName).forEach(weapons::add);
		c.getDndClass().getWeaponProficiencies().stream().map(Weapon::getName).forEach(weapons::add);

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
			.sources(c.getSources())
			.abilities(toAbilitiesOutput(c))
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

	public List<AbilityPdfOutput> toAbilitiesPdf(DndCharacter c) {
		List<AbilityPdfOutput> result = new ArrayList<>();
		var stats = getAbilityStats(c);
		for (Ability ab : Constants.ABILITY_ORDER) {
			result.add(new AbilityPdfOutput(
				ab.getAbbreviation().toLowerCase(),
				stats.get(ab).getScore(),
				stats.get(ab).getMod()));
		}
		return result;		
	}

	public List<SkillPdfOutput> toSkillsPdf(DndCharacter c) {
		List<SkillPdfOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			Ability ab = Constants.SKILL_TO_ABILITY_MAP.get(sk);
			result.add(new SkillPdfOutput(
				sk.getAbbreviation().toLowerCase(),
				getSkillModifier(getAbilityStats(c).get(ab).mod, c.getSkills().contains(sk), c.getLevel()),
				c.getSkills().contains(sk)));
		}
		return result;
	}

	public List<SkillPdfOutput> toSavingThrowsPdf(DndCharacter c) {
		List<SkillPdfOutput> result = new ArrayList<>();
		Set<Ability> saves = c.getDndClass().getSavingThrowProficiencies();
		for (Ability ab : Ability.values()) {
			result.add(new SkillPdfOutput(
				ab.getAbbreviation().toLowerCase(),
				getSkillModifier(getAbilityStats(c).get(ab).mod, saves.contains(ab), c.getLevel()),
				saves.contains(ab)));
		}
		return result;
	}

	public CharacterPdfOutput toPdfOutput(DndCharacter c) {
		return CharacterPdfOutput.builder()
			.info(toInfo(c))
			.stats(toStats(c))
			.abilities(toAbilitiesPdf(c))
			.skills(toSkillsPdf(c))
			.savingThrows(toSavingThrowsPdf(c))
			.armor(c.getArmor())
			.attacks(toAttacks(c))
			.spellcasting(toSpellcastingOutput(c))
			.proficiencies(toProficiencies(c))
			.features(toFeatures(c, false))
			.build();
	}
}
