package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.BonusSet;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Race extends SourceableEntity {

	private String description;

	private List<TextFeature> features;

	private Integer speed;

	@Field("sizes")
	private Set<Size> sizeOptions;

	@Field("abilities_plus_2")
	private BonusSet<Ability> abilityScoresPlus2;

	@Field("abilities_plus_1")
	private BonusSet<Ability> abilityScoresPlus1;

	@Field("skills")
	private BonusSet<Skill> skillProficiencies;

	@Field("languages")
	private BonusSet<Language> languageProficiencies;

	public Race(String id, String name, Source source, String description, List<TextFeature> features, Integer speed,
			Set<Size> sizeOptions, BonusSet<Ability> abilityScoresPlus2, BonusSet<Ability> abilityScoresPlus1,
			BonusSet<Skill> skillProficiencies, BonusSet<Language> languageProficiencies) {
		super(id, name, source);
		this.description = description;
		this.features = features;
		this.speed = speed;
		this.sizeOptions = sizeOptions;
		this.abilityScoresPlus2 = abilityScoresPlus2;
		this.abilityScoresPlus1 = abilityScoresPlus1;
		this.skillProficiencies = skillProficiencies;
		this.languageProficiencies = languageProficiencies;
	}
}
