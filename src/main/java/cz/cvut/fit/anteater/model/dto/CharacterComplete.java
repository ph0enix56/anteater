package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterComplete {
	private String id;

	private CharacterInfo info;

	private CharacterStats stats;

	private List<TextFeature> features;
}
