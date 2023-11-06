package cz.cvut.fit.anteater.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Race extends BaseId {
	private String description;
	private List<TextFeature> features;
	private Integer speed;

	@Field("sizes")
	private Set<Size> sizeOptions;

	@Field("abilities_plus_1")
	private BonusList<Ability> abilityScoresPlus1;

	@Field("abilities_plus_2")
	private BonusList<Ability> abilityScoresPlus2;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("languages")
	private BonusList<Language> languageProficiencies;

	public Race(String name, String description, List<TextFeature> features, Integer speed, Set<Size> sizeOptions,
			BonusList<Ability> abilityScoresPlus1, BonusList<Ability> abilityScoresPlus2,
			BonusList<Skill> skillProficiencies, BonusList<Language> languageProficiencies) {
		super(name);
		this.description = description;
		this.features = features;
		this.speed = speed;
		this.sizeOptions = sizeOptions;
		this.abilityScoresPlus1 = abilityScoresPlus1;
		this.abilityScoresPlus2 = abilityScoresPlus2;
		this.skillProficiencies = skillProficiencies;
		this.languageProficiencies = languageProficiencies;
	}
}
