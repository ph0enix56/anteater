package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Data;

@Data
public class Background {
	@Id
	private String id;
	private String name;

	private String description;
	private String feature;

	private BonusList<Skill> skillProficiencies;
	private BonusList<Language> languageProficiencies;
	private BonusList<Tool> toolProficiencies;
}
