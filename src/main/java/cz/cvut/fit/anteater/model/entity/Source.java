package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;

import lombok.Value;

@Value
public class Source {
	@Id
	private String id;

	private String name;
}
