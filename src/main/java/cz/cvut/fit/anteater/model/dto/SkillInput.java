package cz.cvut.fit.anteater.model.dto;

import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillInput {
	private Skill name;
	private Boolean proficient;
}
