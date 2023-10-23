package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Source {
	@Id
	private String id;
	private String name;

	private String abbreviation;
}
