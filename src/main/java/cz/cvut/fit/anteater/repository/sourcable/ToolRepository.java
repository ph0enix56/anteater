package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class ToolRepository extends SourcableBaseRepository<Tool> {
	public ToolRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Tool.class);
	}
} 
