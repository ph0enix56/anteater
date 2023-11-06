package cz.cvut.fit.anteater.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Language extends BaseId {
	private Boolean exotic;

	public Language(String name, Boolean exotic) {
		super(name);
		this.exotic = exotic;
	}
}
