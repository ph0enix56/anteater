package cz.cvut.fit.anteater.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IdWrapper {

	@NotNull
	private String id;
}
