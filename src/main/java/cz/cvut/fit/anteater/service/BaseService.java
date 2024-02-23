package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	public T findById(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
	}

	private List<Source> convertSources(List<String> sourceIds) {
		List<Source> sourceList = new ArrayList<>();
		for (String source : sourceIds) {
			sourceList.add(sourceRepository.findById(source).orElseThrow(() -> new IllegalArgumentException("Source with given ID not found")));
		}
		return sourceList;
	}

	public List<T> findAll() {
		return repository.findAll();
	}

	public List<T> findByName(String name) {
		return repository.findByNameLike(name);
	}

	public List<T> findBySources(List<String> sourceIds) {
		if (sourceIds.isEmpty()) return findAll();
		return repository.findBySourceIn(convertSources(sourceIds));
	}

	public List<T> findByNameAndSources(String name, List<String> sourceIds) {
		if (name == null && sourceIds == null) return findAll();
		if (name == null) return findBySources(sourceIds);
		if (sourceIds == null) return findByName(name);
		return repository.findByNameLikeAndSourceIn(name, convertSources(sourceIds));
	}

	public Page<T> findByName(String name, Pageable pageable) {
		return repository.findByNameLike(name, pageable);
	}

	public Page<T> findBySources(List<String> sourceIds, Pageable pageable) {
		if (sourceIds.isEmpty()) return repository.findAll(pageable);
		return repository.findBySourceIn(convertSources(sourceIds), pageable);
	}

	public Page<T> findByNameAndSources(String name, List<String> sourceIds, Pageable pageable) {
		if (name == null && sourceIds == null) return repository.findAll(pageable);
		if (name == null) return findBySources(sourceIds, pageable);
		if (sourceIds == null) return findByName(name, pageable);
		return repository.findByNameLikeAndSourceIn(name, convertSources(sourceIds), pageable);
	}

	public Iterable<T> searchPaginated(String name, List<String> sourceIds, Integer page, Integer size) {
		if (page == null && size == null) return findByNameAndSources(name, sourceIds);
		if (page == null) page = 0;
		if (size == null) size = 10;
		return findByNameAndSources(name, sourceIds, Pageable.ofSize(size).withPage(page));
	}

	public T save(T entity, Boolean isUpdate) {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		if (isUpdate != repository.existsById(entity.getId())) throw new IllegalArgumentException("Invalid create/update operation");
		return repository.save(entity);
	}

	public void delete(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (!repository.existsById(id)) throw new NoSuchElementException("Entity with given ID not found");
		repository.deleteById(id);
	}
}
