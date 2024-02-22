package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.model.value.SlotData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpellcastingOutput {

	private Ability ability;

	private Integer modifier;

	private Integer saveDc;

	private List<SlotData> slots;

	private List<Spell> spells;
}
