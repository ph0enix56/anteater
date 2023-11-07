package cz.cvut.fit.anteater.model.value;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Dice {
	@NonNull private Integer amount;
	@NonNull private Integer sides;

	public String getNotation() {
		return amount + "d" + sides;
	}

	public Dice(Integer sides) {
		this(1, sides);
	}
}
