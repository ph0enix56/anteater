package cz.cvut.fit.anteater.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Language extends SourceableEntity {

	private Boolean exotic;

	public Language(String id, String name, Source source, Boolean exotic) {
		super(id, name, source);
		this.exotic = exotic;
	}
}
