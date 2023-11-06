package cz.cvut.fit.anteater.entity;

import cz.cvut.fit.anteater.enumeration.ToolType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tool extends BaseId {
	private ToolType type;

	public Tool(String name, ToolType type) {
		super(name);
		this.type = type;
	}
}
