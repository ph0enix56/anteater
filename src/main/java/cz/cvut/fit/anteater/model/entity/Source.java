package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.Data;

// TODO: make part of BaseId for next version to have a source listed for each item
@Data
public class Source {
	@Id
	@NonNull
	private String abbreviation;

	@NonNull
	private String name;
}
