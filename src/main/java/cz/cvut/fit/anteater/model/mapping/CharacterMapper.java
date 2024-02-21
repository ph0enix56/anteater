package cz.cvut.fit.anteater.model.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.AbilityInput;
import cz.cvut.fit.anteater.model.dto.AbilityOutput;
import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.dto.SkillOutput;
import cz.cvut.fit.anteater.model.dto.SourcableInfo;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.SkillAbilities;
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

	public Integer getSkillModifier(Skill skill, Map<Ability, AbilityStats> abilities, Boolean proficient, Integer level) {
		Ability ability = SkillAbilities.SKILL_TO_ABILITY_MAP.get(skill);
		Integer modifier = abilities.get(ability).mod;
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public Integer getSaveModifier(Ability ability, Map<Ability, AbilityStats> abilities, Boolean proficient, Integer level) {
		Integer modifier = abilities.get(ability).mod;
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public List<SkillOutput> toSkills(DndCharacter c) {
		List<SkillOutput> result = new ArrayList<>();
		for (Skill sk : Skill.values()) {
			Ability ab = SkillAbilities.SKILL_TO_ABILITY_MAP.get(sk);
			result.add(
				new SkillOutput(
					sk.toString(),
					ab.toString(),
					getSkillModifier(sk, getAbilityStats(c), c.getSkills().contains(sk), c.getLevel()),
					c.getSkills().contains(sk),
					sk.getName() + " (" + ab.getAbbreviation() + ")"));
		}
		return result;
	}

	public List<SkillOutput> toSavingThrows(DndCharacter c) {
		List<SkillOutput> result = new ArrayList<>();
		for (Ability ab : Ability.values()) {
			result.add(
				new SkillOutput(
					ab.toString(),
					ab.toString(),
					getSaveModifier(ab, getAbilityStats(c), c.getSaves().contains(ab), c.getLevel()),
					c.getSaves().contains(ab),
					ab.getName()));
		}
		return result;
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
			.tools(c.getTools())
			.languages(c.getLanguages())
			.features(toFeatures(c, false))
			.build();
	}
}
