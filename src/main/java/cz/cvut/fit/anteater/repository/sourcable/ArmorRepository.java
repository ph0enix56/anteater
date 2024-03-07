package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class ArmorRepository extends SourcableBaseRepository<Armor> {
	public ArmorRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Armor.class);
	}
}
