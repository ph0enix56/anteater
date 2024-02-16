package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.SpellSchool;
import cz.cvut.fit.anteater.model.value.SpellComponents;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Spell extends SourceableEntity {

	private Integer level;

	private SpellSchool school;

	private SpellComponents components;

	private String castingTime;

	private String range;

	private String duration;

	private List<String> dndClassIds;

	private String description;

	private String atHigherLevels;
}
