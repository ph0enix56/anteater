package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Language {
	@Id
	private String id;
	private String name;

	private Boolean exotic;
}
