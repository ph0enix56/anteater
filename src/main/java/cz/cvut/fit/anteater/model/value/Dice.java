package cz.cvut.fit.anteater.model.value;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dice {
	private Integer amount;
	private Integer sides;

	public String getNotation() {
		return amount + "d" + sides;
	}

	public Dice(Integer sides) {
		this(1, sides);
	}
}
