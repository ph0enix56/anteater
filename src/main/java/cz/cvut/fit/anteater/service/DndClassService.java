package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class DndClassService extends BaseService<DndClass> {
	public DndClassService(DndClassRepository repository, SourceRepository sourceRepository) {
		super(repository, sourceRepository);
	}
}
