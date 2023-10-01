package cz.cvut.fit.anteater.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.Language;
import cz.cvut.fit.anteater.enumeration.Size;
import lombok.Data;

@Data
public class Race {
	@Id
	public String id;

	private String name;
	private List<Size> sizeOptions;
	private Integer speed;
	private String description;
	private List<AbilityBonus> abilityBonuses;
	private Integer extraSkillProficiency;
	private List<Language> languages;
	private Integer extraLanguage;
}
