package cz.cvut.fit.anteater.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class BaseService<T> {
	private MongoRepository<T, String> repository;

	public BaseService(MongoRepository<T, String> repository) {
		this.repository = repository;
	}

	public Long count() {
		return repository.count();
	}

	public List<T> findAll() {
		return repository.findAll();
	}

	public T findById(String id) {
		return repository.findById(id).orElse(null);
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}
}
