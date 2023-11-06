package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseId {
	@Id
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.NONE)
	@Nullable
	private String id;

	private String name;

	public BaseId(String name) {
		this.name = name;
	}
}
