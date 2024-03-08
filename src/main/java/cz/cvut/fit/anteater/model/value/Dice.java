package cz.cvut.fit.anteater.model.value;


import lombok.Value;

@Value
public class Dice {
	private Integer amount;
	private Integer sides;

	public String getNotation() {
		return amount + "d" + sides;
	}
}
