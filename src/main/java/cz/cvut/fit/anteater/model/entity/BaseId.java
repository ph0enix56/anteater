package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseId {
	@Id
	@Nullable
	private String id;

	private String name;
}
