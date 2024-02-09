package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class SourceableEntity {
	@Id
	private String id;

	private String name;

	private Source source;
}
