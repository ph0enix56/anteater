package cz.cvut.fit.anteater.entity;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BonusList<T> {
	private Integer amount;
	private Set<T> defaults;

	public BonusList(Integer amount, Set<T> defaults) {
		this.amount = amount;
		this.defaults = defaults;
	}
}
