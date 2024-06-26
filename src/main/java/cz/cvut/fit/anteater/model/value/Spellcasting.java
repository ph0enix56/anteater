package cz.cvut.fit.anteater.model.value;

import java.util.List;

import cz.cvut.fit.anteater.enumeration.Ability;
import lombok.Value;

@Value
public class Spellcasting {

	private Ability ability;

	private List<List<Integer>> slots;

	public List<Integer> getSlotsByLevel(int level) {
		return slots.get(level - 1);
	}

	public Integer getMaxSlotLevelForLevel(int level) {
		List<Integer> s = getSlotsByLevel(level);
		var it = s.listIterator(s.size());
		while (it.hasPrevious()) if (it.previous() > 0) return it.nextIndex() + 1;
		return 0;
	}
}
