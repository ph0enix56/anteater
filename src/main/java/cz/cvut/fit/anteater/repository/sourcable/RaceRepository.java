package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class RaceRepository extends SourcableBaseRepository<Race> {
	public RaceRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Race.class);
	}
}
