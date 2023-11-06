package cz.cvut.fit.anteater.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DndClass extends BaseId {
	private String description;
	private List<TextFeature> features;
	private String hitDice;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;

	public DndClass(String name, String description, List<TextFeature> features, String hitDice,
			BonusList<Skill> skillProficiencies, BonusList<Tool> toolProficiencies) {
		super(name);
		this.description = description;
		this.features = features;
		this.hitDice = hitDice;
		this.skillProficiencies = skillProficiencies;
		this.toolProficiencies = toolProficiencies;
	}
}
