package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

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
public class DndClass extends BaseId {

	private String description;

	private List<TextFeature> features;

	private String hitDice;

	@Field("skills")
	private BonusList<Skill> skillProficiencies;

	@Field("tools")
	private BonusList<Tool> toolProficiencies;
}
