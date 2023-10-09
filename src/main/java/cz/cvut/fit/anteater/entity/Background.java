package cz.cvut.fit.anteater.entity;

import java.util.Set;

import cz.cvut.fit.anteater.enumeration.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Background {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private String feature;

	@Column(name = "skill_amount")
	private Integer skillAmount;

	@Column(name = "skill_defaults")
	@Enumerated(value = EnumType.STRING)
	private Set<Skill> skillDefauls;

	//@Column(name = "tool_amount")
	//private Integer toolAmount;

	//@Column(name = "tool_defaults")
	//@ElementCollection(targetClass = Tool.class)
	//private Set<Tool> toolDefaults;
}
