package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BackgroundInput {
	
	@NotEmpty
	private String id;

	private List<String> toolIds;

	private List<String> languageIds;
}
