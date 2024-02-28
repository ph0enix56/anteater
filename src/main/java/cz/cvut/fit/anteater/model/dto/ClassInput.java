package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClassInput {
	
	@NotEmpty
	private String id;

	private String subclass;

	private List<String> toolIds;
}
