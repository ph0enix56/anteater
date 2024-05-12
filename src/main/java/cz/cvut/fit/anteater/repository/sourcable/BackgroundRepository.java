package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class BackgroundRepository extends SourcableBaseRepository<Background> {
	public BackgroundRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Background.class);
	}
}
