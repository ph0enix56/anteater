package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Race extends BaseId {

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
}
