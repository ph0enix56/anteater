package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class InfoInput {

	@NotEmpty
	private String characterName;

	@NotEmpty
	private String playerName;

	@NotEmpty
	private List<String> sourceIds;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;
}
