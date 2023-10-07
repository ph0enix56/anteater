package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.ToolType;
import lombok.Data;

@Data
public class Tool {
	@Id
	private String id;
	private String name;

	private ToolType type;
}
