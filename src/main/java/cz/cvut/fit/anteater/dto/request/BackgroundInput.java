package cz.cvut.fit.anteater.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BackgroundInput {

	@NotNull
	private String id;

	@NotNull
	private List<String> toolIds;

	@NotNull
	private List<String> languageIds;
}
