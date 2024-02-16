package cz.cvut.fit.anteater.model.value;

import java.util.Optional;

import lombok.Data;

@Data
public class SpellComponents {
	private Boolean verbal;
	private Boolean somatic;
	private Optional<String> material;
}
