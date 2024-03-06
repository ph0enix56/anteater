package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInput {

	private String id;

	@NotNull
	@Valid
	private InfoInput info;

	@NotNull
	@Valid
	@JsonProperty("class")
	private ClassInput dndClass;

	@NotNull
	@Valid
	private RaceInput race;

	@NotNull
	@Valid
	private BackgroundInput background;

	@NotNull
	@Size(min = 6, max = 6)
	@Valid
	private List<AbilityInput> abilityScores;
}
