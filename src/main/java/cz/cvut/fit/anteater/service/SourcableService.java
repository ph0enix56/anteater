package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.repository.SourcableDAO;
import cz.cvut.fit.anteater.repository.SourceRepository;

public abstract class SourcableService<T extends SourceableEntity> {
	private SourcableDAO<T> sourcableDAO;
	private SourceRepository sourceRepository;

	public SourcableService(SourcableDAO<T> sourcableDAO, SourceRepository sourceRepository) {
		this.sourcableDAO = sourcableDAO;
		this.sourceRepository = sourceRepository;
	}

	public Long count() {
		return sourcableDAO.count();
	}

	public T findById(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		return sourcableDAO.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
	}

	private List<Source> convertSources(List<String> sourceIds) {
		List<Source> sourceList = new ArrayList<>();
		for (String source : sourceIds) {
			sourceList.add(sourceRepository.findById(source).orElseThrow(() -> new IllegalArgumentException("Source with given ID not found")));
		}
		return sourceList;
	}
	
	public List<T> findAll() {
		return sourcableDAO.findAll();
	}

	public Page<T> search(String name, List<String> sourceIds, Pageable pageable) {
		List<Source> sources = sourceIds == null ? null : convertSources(sourceIds);
		return sourcableDAO.search(name, sources, pageable);
	}

	public T save(T entity, Boolean isUpdate) {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		if (isUpdate != sourcableDAO.existsById(entity.getId())) throw new IllegalArgumentException("Invalid create/update operation");
		return sourcableDAO.save(entity);
	}

	public void delete(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (!sourcableDAO.existsById(id)) throw new NoSuchElementException("Entity with given ID not found");
		sourcableDAO.deleteById(id);
	}
}
