package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.BonusList;
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
	private BonusList<Ability> abilityScoresPlus2;

	@Field("abilities_plus_1")
	private BonusList<Ability> abilityScoresPlus1;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("languages")
	private BonusList<Language> languageProficiencies;

	public Race(String id, String name, Source source, String description, List<TextFeature> features, Integer speed,
			Set<Size> sizeOptions, BonusList<Ability> abilityScoresPlus2, BonusList<Ability> abilityScoresPlus1,
			BonusList<Skill> skillProficiencies, BonusList<Language> languageProficiencies) {
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
