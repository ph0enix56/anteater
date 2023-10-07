package cz.cvut.fit.anteater.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Data;

@Data
public class Race {
	@Id
	private String id;
	private String name;

	private String description;
	private List<String> features;

	private Integer speed;
	private Set<Size> sizeOptions;

	private BonusList<Ability> abilityScoresPlus1;
	private BonusList<Ability> abilityScoresPlus2;
	private BonusList<Skill> skillProficiencies;
	private BonusList<Language> languageProficiencies;
}
