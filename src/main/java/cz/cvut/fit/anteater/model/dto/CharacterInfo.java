package cz.cvut.fit.anteater.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInfo {
	private String characterName;

	private String playerName;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;

	@JsonProperty("class")
	private SourcableInfo dndClass;

	private SourcableInfo race;

	private SourcableInfo background;

	private Integer level;

	private String subclass;
}
