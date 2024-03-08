package cz.cvut.fit.anteater.model.entity;

import cz.cvut.fit.anteater.enumeration.ToolType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Tool extends SourceableEntity {

	private ToolType type;

	public Tool(String id, String name, Source source, ToolType type) {
		super(id, name, source);
		this.type = type;
	}
}
