package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class SourceableEntity {
	@Id
	private String id;

	private String name;

	private Source source;
}
