package cz.cvut.fit.anteater.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Spell {
	@Id
	public String id;
	
	public String name;

	@Field("entries")
	public String description;

	public Integer level;

	public String school;

	@Field("damageInflict")
	public List<String> damageType;

	public Spell() {}

	public Spell(String name, String description, Integer level, String school, List<String> damageType) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.school = school;
		this.damageType = damageType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public List<String> getDamageType() {
		return damageType;
	}

	public void setDamageType(List<String> damageType) {
		this.damageType = damageType;
	}

	@Override
	public String toString() {
		return "Spell [id=" + id + ", name=" + name + ", description=" + description + ", level=" + level + ", school="
			+ school + ", damageType=" + damageType + "]";
	}
}
