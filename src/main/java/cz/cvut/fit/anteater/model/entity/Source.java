package cz.cvut.fit.anteater.model.entity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Source {
	@Id
	private String id;

	private String name;
}
