package cz.cvut.fit.anteater.dto.request;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaceInput {

	@NotNull
	private String id;

	private Size size;

	@NotNull
	private List<String> languageIds;
}
