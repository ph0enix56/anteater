package cz.cvut.fit.anteater.service;

import java.util.List;

import cz.cvut.fit.anteater.model.entity.BaseId;
import cz.cvut.fit.anteater.repository.BaseRepository;

public abstract class BaseService<T extends BaseId> {
	private BaseRepository<T> repository;

	public BaseService(BaseRepository<T> repository) {
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

	public T findbyName(String name) {
		return repository.findByName(name);
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}
}
