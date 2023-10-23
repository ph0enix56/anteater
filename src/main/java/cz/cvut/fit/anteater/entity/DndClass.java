package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class DndClass {
	@Id
	private String id;
	private String name;

	//TODO: add other fields
}
