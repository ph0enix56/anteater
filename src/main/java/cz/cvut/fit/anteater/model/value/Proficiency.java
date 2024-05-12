package cz.cvut.fit.anteater.model.value;

import cz.cvut.fit.anteater.enumeration.ProficiencySource;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import lombok.Value;

@Value
public class Proficiency<T extends SourceableEntity> {

	private T item;

	private ProficiencySource from;
}
