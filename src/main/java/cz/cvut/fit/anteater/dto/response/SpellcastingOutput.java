package cz.cvut.fit.anteater.dto.response;

import java.util.List;

import cz.cvut.fit.anteater.model.entity.Spell;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class SpellcastingOutput {

	@Value
	public static class SlotData {

		private Integer level;

		private Integer count;
	}

	private String abilityAbbreviation;

	private Integer modifier;

	private Integer saveDc;

	private List<SlotData> slots;

	private List<Spell> spells;
}
