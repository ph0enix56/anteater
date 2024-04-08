package cz.cvut.fit.anteater.model.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DiceTests {

	@Test
	void notationForDice() {
		Dice dice = new Dice(2, 6);
		assertEquals(dice.getNotation(), "2d6");

		dice = new Dice(1, 20);
		assertEquals(dice.getNotation(), "1d20");
	}
}
