package cz.cvut.fit.anteater.dto.request;

import cz.cvut.fit.anteater.enumeration.Skill;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillInput {

	@NotNull
	private Skill name;

	@NotNull
	private Boolean proficient;
}
