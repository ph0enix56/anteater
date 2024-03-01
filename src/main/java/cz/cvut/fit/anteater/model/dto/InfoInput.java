package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InfoInput {

	private String characterName;

	private String playerName;

	@NotNull
	private List<String> sourceIds;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;
}
