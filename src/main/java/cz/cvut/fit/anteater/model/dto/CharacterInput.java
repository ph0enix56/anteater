package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInput {

	private String id;

	@NotNull
	private InfoInput basicInfo;

	@NotNull
	@JsonProperty("class")
	private ClassInput dndClass;

	@NotNull
	private RaceInput race;

	@NotNull
	private BackgroundInput background;

	@NotNull
	@Size(min = 6, max = 6)
	private List<AbilityInput> abilityScores;
}
