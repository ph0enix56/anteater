package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.DndCharacter;

public interface DndCharacterRepository extends MongoRepository<DndCharacter, String> {}
