package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class WeaponRepository extends SourcableBaseRepository<Weapon> {
	public WeaponRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Weapon.class);
	}
}
