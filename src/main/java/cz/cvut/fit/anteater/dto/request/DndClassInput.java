package cz.cvut.fit.anteater.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DndClassInput {
	
	@NotNull
	private String id;

	@NotNull
	private String subclass;

	@NotNull
	private List<String> toolIds;
}
