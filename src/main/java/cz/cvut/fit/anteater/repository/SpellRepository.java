package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Spell;

public interface SpellRepository extends MongoRepository<Spell, String> {
	public Spell findByName(String name);
}
