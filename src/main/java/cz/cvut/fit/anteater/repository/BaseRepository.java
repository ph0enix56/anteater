package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.BaseId;

public interface BaseRepository<T extends BaseId> extends MongoRepository<T, String> {
	public T findByName(String name);
}
