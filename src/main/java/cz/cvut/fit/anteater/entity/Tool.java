package cz.cvut.fit.anteater.entity;

import org.springframework.data.annotation.Id;

import cz.cvut.fit.anteater.enumeration.ToolType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tool {
	@Id
	private String id;
	private String name;

	private ToolType type;
}
