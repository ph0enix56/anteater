package cz.cvut.fit.anteater.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterShort {
	private String id;
	private CharacterInfo info;
}
