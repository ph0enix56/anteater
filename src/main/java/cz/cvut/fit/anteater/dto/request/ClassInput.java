package cz.cvut.fit.anteater.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassInput {
	
	@NotNull
	private String id;

	private String subclass;

	@NotNull
	private List<String> toolIds;
}
