package cz.cvut.fit.anteater.model.entity;

import cz.cvut.fit.anteater.enumeration.ToolType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tool extends SourceableEntity {

	private ToolType type;
}
