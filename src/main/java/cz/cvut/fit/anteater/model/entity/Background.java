package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Background extends SourceableEntity {

	private String description;

	private List<TextFeature> features;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("languages")
	private BonusList<Language> languageProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;

	public Background(String id, String name, Source source, String description, List<TextFeature> features,
			BonusList<Skill> skillProficiencies, BonusList<Language> languageProficiencies,
			BonusList<Tool> toolProficiencies) {
		super(id, name, source);
		this.description = description;
		this.features = features;
		this.skillProficiencies = skillProficiencies;
		this.languageProficiencies = languageProficiencies;
		this.toolProficiencies = toolProficiencies;
	}
}
