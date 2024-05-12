package cz.cvut.fit.anteater.model.value;

import java.util.Set;

import lombok.Value;

@Value
public class BonusSet<T> {
	private Integer amount;
	private Set<T> defaults;

	public BonusSet(Integer amount, Set<T> defaults) {
		if (defaults.size() > amount) throw new IllegalArgumentException("Amount must be greater than or equal to the number of defaults.");
		this.amount = amount;
		this.defaults = defaults;
	}
}
