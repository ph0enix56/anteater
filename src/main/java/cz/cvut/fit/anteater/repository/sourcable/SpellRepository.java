package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class SpellRepository extends SourcableBaseRepository<Spell> {
	public SpellRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Spell.class);
	}
}
