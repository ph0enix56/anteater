package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Data;

@Data
public class Background {
	@Id
	private String id;
	private String name;

	private String description;
	private String feature;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("languages")
	private BonusList<Language> languageProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;
}
