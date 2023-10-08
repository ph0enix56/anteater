package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Race;

public interface RaceRepository extends MongoRepository<Race, String> {
	public Race findByName(String name);
}
