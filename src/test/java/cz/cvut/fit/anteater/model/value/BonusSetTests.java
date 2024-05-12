package cz.cvut.fit.anteater.model.value;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class BonusSetTests {
	
	@Test
	public void constructorPositive() {
		try {
			new BonusSet<Integer>(1, Set.of(1));
			new BonusSet<Integer>(5, Set.of());
			new BonusSet<Integer>(0, Set.of());
		} catch (IllegalArgumentException e) {
			throw new AssertionError("Amount must be greater than or equal to the number of defaults.");
		}
	}

	@Test
	public void constructorNegative() {
		assertThrows(IllegalArgumentException.class, () -> new BonusSet<Integer>(0, Set.of(1)));
		assertThrows(IllegalArgumentException.class, () -> new BonusSet<Integer>(5, Set.of(1, 2, 3, 4, 5, 6)));
	}
}
