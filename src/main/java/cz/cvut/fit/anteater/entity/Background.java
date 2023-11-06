package cz.cvut.fit.anteater.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Background extends BaseId {
	private String description;
	private List<TextFeature> features;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("languages")
	private BonusList<Language> languageProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;

	public Background(String name, String description, List<TextFeature> features,
			BonusList<Skill> skillProficiencies, BonusList<Language> languageProficiencies,
			BonusList<Tool> toolProficiencies) {
		super(name);
		this.description = description;
		this.features = features;
		this.skillProficiencies = skillProficiencies;
		this.languageProficiencies = languageProficiencies;
		this.toolProficiencies = toolProficiencies;
	}
}
