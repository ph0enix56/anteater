package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Size;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RaceInput {

	@NotEmpty
	private String id;

	private Size size;

	private List<String> languageIds;
}
