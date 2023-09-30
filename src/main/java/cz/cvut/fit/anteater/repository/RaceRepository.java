package cz.cvut.fit.anteater.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Race;

public interface RaceRepository extends MongoRepository<Race, String> {
	public List<Race> findByName(String name);
	public List<Race> findBySpeed(Integer speed);
}
