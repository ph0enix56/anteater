package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.repository.sourcable.DndClassRepository;

@Service
public class DndClassService extends SourcableBaseService<DndClass> {
	public DndClassService(DndClassRepository repo) {
		super(repo);
	}
}
