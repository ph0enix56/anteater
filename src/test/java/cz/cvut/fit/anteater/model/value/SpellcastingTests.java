package cz.cvut.fit.anteater.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class SpellcastingTests {

	List<List<Integer>> filledSlots = List.of(
		List.of(2, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(3, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(4, 2, 0, 0, 0, 0, 0, 0, 0),
		List.of(4, 3, 0, 0, 0, 0, 0, 0, 0),
		List.of(4, 3, 2, 0, 0, 0, 0, 0, 0),
		List.of(4, 3, 3, 0, 0, 0, 0, 0, 0),
		List.of(4, 3, 3, 1, 0, 0, 0, 0, 0),
		List.of(4, 3, 3, 2, 0, 0, 0, 0, 0),
		List.of(4, 3, 3, 3, 1, 0, 0, 0, 0),
		List.of(4, 3, 3, 3, 2, 0, 0, 0, 0),
		List.of(4, 3, 3, 3, 2, 1, 0, 0, 0),
		List.of(4, 3, 3, 3, 2, 1, 0, 0, 0),
		List.of(4, 3, 3, 3, 2, 1, 1, 0, 0),
		List.of(4, 3, 3, 3, 2, 1, 1, 0, 0),
		List.of(4, 3, 3, 3, 2, 1, 1, 1, 0),
		List.of(4, 3, 3, 3, 2, 1, 1, 1, 0),
		List.of(4, 3, 3, 3, 2, 1, 1, 1, 1),
		List.of(4, 3, 3, 3, 3, 1, 1, 1, 1),
		List.of(4, 3, 3, 3, 3, 2, 1, 1, 1),
		List.of(4, 3, 3, 3, 3, 2, 2, 1, 1));

	List<List<Integer>> emptySlots = List.of(
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0),
		List.of(0, 0, 0, 0, 0, 0, 0, 0, 0));

	@Test
	void getSlotsAtLevel1() {
		Spellcasting spellcasting = new Spellcasting(null, filledSlots);
		assertEquals(List.of(2, 0, 0, 0, 0, 0, 0, 0, 0), spellcasting.getSlotsByLevel(1));
	}

	@Test
	void getSlotsAtLevel20() {
		Spellcasting spellcasting = new Spellcasting(null, filledSlots);
		assertEquals(List.of(4, 3, 3, 3, 3, 2, 2, 1, 1), spellcasting.getSlotsByLevel(20));
	}

	@Test
	void maxSlotLevelLowLevel() {
		Spellcasting spellcasting = new Spellcasting(null, filledSlots);
		assertEquals(3, spellcasting.getMaxSlotLevelForLevel(5));
	}

	@Test
	void maxSlotLevelHighLevel() {
		Spellcasting spellcasting = new Spellcasting(null, filledSlots);
		assertEquals(9, spellcasting.getMaxSlotLevelForLevel(17));
	}

	@Test
	void maxSlotLevelNoSlots() {
		Spellcasting spellcasting = new Spellcasting(null, emptySlots);
		assertEquals(0, spellcasting.getMaxSlotLevelForLevel(20));
	}
}
