package cz.cvut.fit.anteater.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseNameRepository<T> extends MongoRepository<T, String> {
	public List<T> findByName(String name);
}
