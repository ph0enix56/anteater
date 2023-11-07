package cz.cvut.fit.anteater.model.value;

import java.util.Set;

import lombok.NonNull;
import lombok.Value;

@Value
public class BonusList<T> {
	@NonNull private Integer amount;
	@NonNull private Set<T> defaults;
}
