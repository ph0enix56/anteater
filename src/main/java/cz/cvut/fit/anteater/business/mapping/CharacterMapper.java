package cz.cvut.fit.anteater.business.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.constants.Constants;
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
import cz.cvut.fit.anteater.enumeration.WeaponType;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.Character;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.TextFeature;

@Component
public class CharacterMapper {

	public SourcableInfo toSrcInfo(SourceableEntity src) {
		return new SourcableInfo(src.getId(), src.getName(), src.getSource().getId());
	}

	public CharacterInfo toInfo(Character c) {
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

	public CharacterShort toShort(Character c) {
		return new CharacterShort(c.getId(), toInfo(c));
	}

	public CharacterStats toStats(Character c) {
		return CharacterStats.builder()
			.proficiencyBonus(c.getProficiencyBonus())
			.initiative(c.getInitiative())
			.speed(c.getSpeed())
			.hitDice(new Dice(c.getLevel(), c.getDndClass().getHitDice().getSides()))
			.hitPoints(c.getHitPoints())
			.armorClass(c.getArmorClass())
			.build();
	}

	public List<AbilityOutput> toAbilitiesOutput(Character c) {
		List<AbilityOutput> result = new ArrayList<>();
		for (Ability ab : Constants.ABILITY_ORDER) {
			var input = c.getAbilities().get(ab);
			result.add(new AbilityOutput(
				ab.toString(),
				input.getScore(),
				input.getUpByOne(),
				input.getUpByTwo(),
				c.getAbilityScore(ab),
				c.getAbilityModifier(ab),
				ab.getName()));
		}
		return result;
	}

	public List<SkillOutput> toSkills(Character c) {
		List<SkillOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			Ability ab = Constants.SKILL_TO_ABILITY_MAP.get(sk);
			result.add(new SkillOutput(
				sk.toString(),
				ab.toString(),
				c.getSkillModifier(sk),
				c.getSkills().contains(sk),
				sk.getName() + " (" + ab.getAbbreviation() + ")"));
		}
		return result;
	}

	public List<SkillOutput> toSavingThrows(Character c) {
		List<SkillOutput> result = new ArrayList<>();
		Set<Ability> saves = c.getDndClass().getSavingThrowProficiencies();
		for (Ability ab : Ability.values()) {
			result.add(new SkillOutput(
				ab.toString(),
				ab.toString(),
				c.getSaveModifier(ab),
				saves.contains(ab),
				ab.getName()));
		}
		return result;
	}

	public List<AttackOutput> toAttacks(Character c) {
		List<AttackOutput> result = new ArrayList<>();
		for (var i : c.getWeapons()) {
			int attackBonus = c.getWeaponAttackBonus(i);
			int damageMod = c.getWeaponDamageModifier(i);

			StringBuilder dmgBuilder = new StringBuilder(i.getDamage().getNotation());
			if (damageMod > 0) dmgBuilder.append(" + ").append(damageMod);
			else if (damageMod < 0) dmgBuilder.append(" - ").append(-damageMod);
			String damage = dmgBuilder.append(" ").append(i.getDamageType()).toString();

			result.add(new AttackOutput(i.getId(), i.getName(), attackBonus, damage));
		}
		return result;
	}

	public SpellcastingOutput toSpellcastingOutput(Character c) {
		if (c.getDndClass().getSpellcasting() == null) return null;
		Ability spellAbility = c.getDndClass().getSpellcasting().getAbility();

		List<SpellcastingOutput.SlotData> slotsRes = new ArrayList<>();
		List<Integer> slots = c.getDndClass().getSpellcasting().getSlotsByLevel(c.getLevel());
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i) > 0) slotsRes.add(new SpellcastingOutput.SlotData(i + 1, slots.get(i)));
		}

		return SpellcastingOutput.builder()
			.abilityAbbreviation(spellAbility.getAbbreviation())
			.modifier(c.getSpellAttackModifier())
			.saveDc(c.getSpellSaveDC())
			.slots(slotsRes)
			.spells(c.getSpells())
			.build();
	}

	public ProficiencyList toProficiencies(Character c) {
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

	public List<TextFeature> toFeatures(Character c, Boolean allLevels) {
		List<TextFeature> features = new ArrayList<>();
		features.addAll(c.getBackground().getFeatures());
		features.addAll(c.getRace().getFeatures());
		features.addAll(c.getDndClass().getFeatures());
		if (!allLevels) features.removeIf(f -> f.getLevelMinimum() > c.getLevel());
		return features;
	}

	public CharacterComplete toComplete(Character c) {
		return CharacterComplete.builder()
			.id(c.getId())
			.info(toInfo(c))
			.stats(toStats(c))
			.sources(c.getSources())
			.abilities(toAbilitiesOutput(c))
			.skills(toSkills(c))
			.savingThrows(toSavingThrows(c))
			.armorEquipped(c.getArmor().getId() != null)
			.armor(c.getArmor())
			.attacks(toAttacks(c))
			.spellcasting(toSpellcastingOutput(c))
			.proficiencies(toProficiencies(c))
			.tools(c.getTools())
			.languages(c.getLanguages())
			.features(toFeatures(c, false))
			.build();
	}

	public List<AbilityPdfOutput> toAbilitiesPdf(Character c) {
		List<AbilityPdfOutput> result = new ArrayList<>();
		for (Ability ab : Constants.ABILITY_ORDER) {
			result.add(new AbilityPdfOutput(
				ab.getAbbreviation().toLowerCase(),
				c.getAbilityScore(ab),
				c.getAbilityModifier(ab)));
		}
		return result;		
	}

	public List<SkillPdfOutput> toSkillsPdf(Character c) {
		List<SkillPdfOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			result.add(new SkillPdfOutput(
				sk.getAbbreviation().toLowerCase(),
				c.getSkillModifier(sk),
				c.getSkills().contains(sk)));
		}
		return result;
	}

	public List<SkillPdfOutput> toSavingThrowsPdf(Character c) {
		List<SkillPdfOutput> result = new ArrayList<>();
		Set<Ability> saves = c.getDndClass().getSavingThrowProficiencies();
		for (Ability ab : Ability.values()) {
			result.add(new SkillPdfOutput(
				ab.getAbbreviation().toLowerCase(),
				c.getSaveModifier(ab),
				saves.contains(ab)));
		}
		return result;
	}

	public CharacterPdfOutput toPdfOutput(Character c) {
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
