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

	private InfoInput basicInfo;

	@JsonProperty("class")
	private ClassInput dndClass;

	private RaceInput race;

	private BackgroundInput background;

	@NotNull
	@Size(min = 6, max = 6)
	private List<AbilityInput> abilityScores;
}
