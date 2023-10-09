package cz.cvut.fit.anteater.entity;

import java.util.List;
import java.util.Set;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

// TODO: add proper annotations and fields
@Data
@Entity
public class Race {
	@Id
	private Long id;
	private String name;

	private String description;
	private List<String> features;

	private Integer speed;
	private Set<Size> sizeOptions;

	//private BonusList<Ability> abilityScoresPlus1;
	//private BonusList<Ability> abilityScoresPlus2;
	//private BonusList<Skill> skillProficiencies;
	//private BonusList<Language> languageProficiencies;
}
