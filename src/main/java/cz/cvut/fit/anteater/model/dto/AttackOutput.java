package cz.cvut.fit.anteater.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttackOutput {
	private String id;
	private String name;
	private Integer attackBonus;
	private String damage;
}
