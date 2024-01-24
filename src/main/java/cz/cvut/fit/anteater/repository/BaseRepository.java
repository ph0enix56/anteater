package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;

public interface BaseRepository<T extends SourceableEntity> extends MongoRepository<T, String> {
	public T findByName(String name);
}
