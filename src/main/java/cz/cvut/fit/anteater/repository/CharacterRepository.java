package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Character;

@Repository
public interface CharacterRepository extends MongoRepository<Character, String> {}
