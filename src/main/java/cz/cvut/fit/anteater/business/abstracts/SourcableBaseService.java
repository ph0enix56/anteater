package cz.cvut.fit.anteater.business.abstracts;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

public abstract class SourcableBaseService<T extends SourceableEntity> {
	private SourcableBaseRepository<T> sourcableRepo;

	public SourcableBaseService(SourcableBaseRepository<T> sourcableRepo) {
		this.sourcableRepo = sourcableRepo;
	}

	public Long count() {
		return sourcableRepo.count();
	}

	public T findById(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		return sourcableRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
	}

	public List<T> findAll() {
		return sourcableRepo.findAll();
	}

	public Page<T> search(String name, List<String> sourceIds, Integer page, Integer size) {
		Pageable pageable;
		if (page == null && size == null) pageable = Pageable.unpaged();
		else if (page == null) pageable = PageRequest.of(0, size);
		else if (size == null) pageable = PageRequest.of(page, 10);
		else pageable = PageRequest.of(page, size);
		return sourcableRepo.search(name, sourceIds, pageable);
	}

	public T save(T entity, Boolean isUpdate) {
		if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
		if (!isUpdate && entity.getId() != null) throw new IllegalArgumentException("ID must be null for create operation");
		if (isUpdate && !sourcableRepo.existsById(entity.getId())) throw new NoSuchElementException("Entity with given ID not found");
		return sourcableRepo.save(entity);
	}

	public void delete(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (!sourcableRepo.existsById(id)) throw new NoSuchElementException("Entity with given ID not found");
		sourcableRepo.deleteById(id);
	}
}
