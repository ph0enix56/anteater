package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.Spell;

// TODO: spells not yet implemented
public interface SpellRepository extends MongoRepository<Spell, String> {}
