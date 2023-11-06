package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseRepository extends MongoRepository<BaseId, String> {
	
}
