package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class DndClassRepository extends SourcableBaseRepository<DndClass> {
	public DndClassRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, DndClass.class);
	}
}
