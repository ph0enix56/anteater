package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.BonusSet;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Background extends SourceableEntity {

	private String description;

	private List<TextFeature> features;

	@Field("skills")
	private BonusSet<Skill> skillProficiencies;

	@Field("languages")
	private BonusSet<Language> languageProficiencies;

	@Field("tools")
	private BonusSet<Tool> toolProficiencies;

	public Background(String id, String name, Source source, String description, List<TextFeature> features,
			BonusSet<Skill> skillProficiencies, BonusSet<Language> languageProficiencies,
			BonusSet<Tool> toolProficiencies) {
		super(id, name, source);
		this.description = description;
		this.features = features;
		this.skillProficiencies = skillProficiencies;
		this.languageProficiencies = languageProficiencies;
		this.toolProficiencies = toolProficiencies;
	}
}
