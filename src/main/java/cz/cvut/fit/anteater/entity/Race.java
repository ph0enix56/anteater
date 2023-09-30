package cz.cvut.fit.anteater.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.Language;
import cz.cvut.fit.anteater.enumeration.Size;

public class Race {
	@Id
	public String id;

	public String name;
	public List<Size> sizeOptions;
	public Integer speed;
	public String description;
	public List<AbilityBonus> abilityBonuses;
	public int extraSkillProficiency;
	public List<Language> languages;
	public int extraLanguage;

	public Race(String name, List<Size> sizeOptions, Integer speed, String description,
			List<AbilityBonus> abilityBonuses, int extraSkillProficiency, List<Language> languages, int extraLanguage) {
		this.name = name;
		this.sizeOptions = sizeOptions;
		this.speed = speed;
		this.description = description;
		this.abilityBonuses = abilityBonuses;
		this.extraSkillProficiency = extraSkillProficiency;
		this.languages = languages;
		this.extraLanguage = extraLanguage;
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

	public List<Size> getSizeOptions() {
		return sizeOptions;
	}

	public void setSizeOptions(List<Size> sizeOptions) {
		this.sizeOptions = sizeOptions;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AbilityBonus> getAbilityBonuses() {
		return abilityBonuses;
	}

	public void setAbilityBonuses(List<AbilityBonus> abilityBonuses) {
		this.abilityBonuses = abilityBonuses;
	}

	public int getExtraSkillProficiency() {
		return extraSkillProficiency;
	}

	public void setExtraSkillProficiency(int extraSkillProficiency) {
		this.extraSkillProficiency = extraSkillProficiency;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public int getExtraLanguage() {
		return extraLanguage;
	}

	public void setExtraLanguage(int extraLanguage) {
		this.extraLanguage = extraLanguage;
	}

	@Override
	public String toString() {
		return "Race [name=" + name + ", sizeOptions=" + sizeOptions + ", speed=" + speed + ", description="
				+ description + ", abilityBonuses=" + abilityBonuses + ", extraSkillProficiency="
				+ extraSkillProficiency + ", languages=" + languages + ", extraLanguage=" + extraLanguage + "]";
	}
}
