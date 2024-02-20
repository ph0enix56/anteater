package cz.cvut.fit.anteater.model.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.AbilityInput;
import cz.cvut.fit.anteater.model.dto.AbilityOutput;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.dto.SkillStats;
import cz.cvut.fit.anteater.model.dto.SourcableInfo;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.SkillAbilities;
import cz.cvut.fit.anteater.model.value.TextFeature;

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

	public Integer getProficiencyBonus(Integer level) {
		return (level - 1) / 4 + 2;
	}

	public Integer getAbilityModifier(Integer abilityScore) {
		return (abilityScore - 10) / 2;
	}

	public Integer getSkillModifier(Integer abilityModifier, Boolean proficient, Integer level) {
		return proficient ? abilityModifier + getProficiencyBonus(level) : abilityModifier;
	}

	public Integer getHitPoints(Dice hitDice, Integer conScore, Integer level) {
		Integer initialHP = hitDice.getSides() + getAbilityModifier(conScore);
		Integer perLevelHP = hitDice.getSides() / 2 + 1 + getAbilityModifier(conScore);
		return initialHP + perLevelHP * (level - 1);
	}

	public Integer getArmorClass(Armor armor, Map<Ability, AbilityOutput> abilities) {
		Integer result = armor.getBaseArmorClass();
		for (var i : armor.getBonuses()) {
			result += Math.min(i.getMax(), abilities.get(i.getAbility()).getModifier());
		}
		return result;
	}

	public CharacterStats toStats(DndCharacter c) {
		Map<Ability, AbilityOutput> abilities = toAbilities(c);
		return CharacterStats.builder()
		.proficiencyBonus(getProficiencyBonus(c.getLevel()))
		.initiative(abilities.get(Ability.dexterity).getModifier())
		.speed(c.getRace().getSpeed())
			.hitDice(c.getDndClass().getHitDice())
			.hitPoints(getHitPoints(c.getDndClass().getHitDice(), abilities.get(Ability.constitution).getModifier(), c.getLevel()))
			.armorClass(getArmorClass(c.getArmor(), abilities))
			.build();
		}

	public AbilityOutput getAbilityOutput(AbilityInput in) {
		Integer score = in.getScore();
		Boolean upByOne = in.getUpByOne();
		Boolean upByTwo = in.getUpByTwo();
		Integer result = score + (upByOne ? 1 : 0) + (upByTwo ? 2 : 0);
		Integer modifier = getAbilityModifier(result);
		return new AbilityOutput(score, upByOne, upByTwo, result, modifier);
	}

	public Map<Ability, AbilityOutput> toAbilities(DndCharacter c) {
		Map<Ability, AbilityOutput> result = new HashMap<>();
		for (var i : c.getAbilities().entrySet()) {
			result.put(i.getKey(), getAbilityOutput(i.getValue()));
		}
		return result;
	}

	public Integer getSkillModifier(Skill skill, Map<Ability, AbilityOutput> abilities, Boolean proficient, Integer level) {
		Ability ability = SkillAbilities.SKILL_TO_ABILITY_MAP.get(skill);
		Integer modifier = abilities.get(ability).getModifier();
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public Integer getSaveModifier(Ability ability, Map<Ability, AbilityOutput> abilities, Boolean proficient, Integer level) {
		Integer modifier = abilities.get(ability).getModifier();
		return proficient ? modifier + getProficiencyBonus(level) : modifier;
	}

	public Map<Skill, SkillStats> toSkills(DndCharacter c) {
		Map<Skill, SkillStats> skills = new TreeMap<>();
		for (Skill sk : Skill.values()) {
			skills.put(sk,
				new SkillStats(
					getSkillModifier(sk, toAbilities(c), c.getSkills().contains(sk), c.getLevel()),
					c.getSkills().contains(sk)));
		}
		return skills;
	}
	
	public Map<Ability, SkillStats> toSavingThrows(DndCharacter c) {
		Map<Ability, SkillStats> saves = new TreeMap<>();
		for (Ability ab : Ability.values()) {
			saves.put(ab,
				new SkillStats(
					getSaveModifier(ab, toAbilities(c), c.getSaves().contains(ab), c.getLevel()),
					c.getSaves().contains(ab)));
		}
		return saves;
	}

	public List<TextFeature> toFeatures(DndCharacter c, Boolean allLevels) {
		List<TextFeature> features = new ArrayList<>();
		features.addAll(c.getBackground().getFeatures());
		features.addAll(c.getRace().getFeatures());
		features.addAll(c.getDndClass().getFeatures());
		if (!allLevels) features.removeIf(f -> f.getLevelMinimum() > c.getLevel());
		return features;
	}
}
