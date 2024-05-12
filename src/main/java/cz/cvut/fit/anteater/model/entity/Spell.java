package cz.cvut.fit.anteater.model.entity;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.SpellSchool;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Spell extends SourceableEntity {

	@Value
	public static class SpellComponents {

		private Boolean verbal;

		private Boolean somatic;

		private String material;
	}

	private Integer level;

	private SpellSchool school;

	private SpellComponents components;

	private String castingTime;

	private String range;

	private String duration;

	private List<String> dndClassIds;

	private String description;

	private String atHigherLevels;

	public Spell(String id, String name, Source source, Integer level, SpellSchool school, SpellComponents components,
			String castingTime, String range, String duration, List<String> dndClassIds, String description,
			String atHigherLevels) {
		super(id, name, source);
		this.level = level;
		this.school = school;
		this.components = components;
		this.castingTime = castingTime;
		this.range = range;
		this.duration = duration;
		this.dndClassIds = dndClassIds;
		this.description = description;
		this.atHigherLevels = atHigherLevels;
	}
}
