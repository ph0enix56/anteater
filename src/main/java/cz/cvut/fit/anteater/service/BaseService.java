package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.repository.BaseRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

public abstract class BaseService<T extends SourceableEntity> {
	private BaseRepository<T> repository;
	private SourceRepository sourceRepository;

	public BaseService(BaseRepository<T> repository, SourceRepository sourceRepository) {
		this.repository = repository;
		this.sourceRepository = sourceRepository;
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

	public List<T> findByName(String name) {
		System.out.println("Searching for name: \"" + name + "\"");
		return repository.findByName(name);
	}

	private List<Source> convertSources(List<String> sourceIds) {
		List<Source> sourceList = new ArrayList<>();
		for (String source : sourceIds) {
			Optional<Source> s = sourceRepository.findById(source);
			if (s.isPresent()) sourceList.add(s.get());
		}
		System.out.println("Searching for sources: " + sourceList);
		return sourceList;
	}

	public List<T> findBySourceList(List<String> sourceIds) {
		return repository.findBySourceIn(convertSources(sourceIds));
	}

	public List<T> findByNameAndSources(String name, List<String> sourceIds) {
		if (name == null && sourceIds == null) return findAll();
		if (name == null) return findBySourceList(sourceIds);
		if (sourceIds == null) return findByName(name);
		return repository.findByNameAndSourceIn(name, convertSources(sourceIds));
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}
}
