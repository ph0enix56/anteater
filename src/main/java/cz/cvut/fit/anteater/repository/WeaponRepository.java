package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Weapon;

@Repository
public class WeaponRepository extends SourcableDAO<Weapon> {
	public WeaponRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Weapon.class);
	}
}
