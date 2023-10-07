package cz.cvut.fit.anteater.entity;

import java.util.Set;

import lombok.Data;

@Data
public class BonusList<T> {
	private Integer amount;
	private Set<T> defaults;
}
