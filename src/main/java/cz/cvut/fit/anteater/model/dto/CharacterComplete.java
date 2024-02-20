package cz.cvut.fit.anteater.model.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.mapping.MapToCoupleArraySerializer;
import cz.cvut.fit.anteater.model.value.Proficiency;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterComplete {
	private String id;

	private CharacterInfo info;

	private CharacterStats stats;

	@JsonSerialize(using = MapToCoupleArraySerializer.class)
	private Map<Ability, AbilityOutput> abilities;

	@JsonSerialize(using = MapToCoupleArraySerializer.class)
	private Map<Skill, SkillStats> skills;

	@JsonSerialize(using = MapToCoupleArraySerializer.class)
	private Map<Ability, SkillStats> savingThrows;

	private List<Proficiency<Tool>> tools;

	private List<Proficiency<Language>> languages;

	private List<TextFeature> features;
}
