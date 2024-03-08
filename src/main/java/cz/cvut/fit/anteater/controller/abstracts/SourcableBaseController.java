package cz.cvut.fit.anteater.controller.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class SourcableBaseController<T extends SourceableEntity> {
	private SourcableBaseService<T> sourcableService;

	public SourcableBaseController(SourcableBaseService<T> sourcableService) {
		this.sourcableService = sourcableService;
	}

	@GetMapping
	public Page<T> search(@RequestParam(required = false) String name,
			@RequestParam(required = false) List<String> sources,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		return sourcableService.search(name, sources, page, size);
	}

	@GetMapping("/{id}")
	public T getById(@RequestParam String id) {
		return sourcableService.findById(id);
	}
}
