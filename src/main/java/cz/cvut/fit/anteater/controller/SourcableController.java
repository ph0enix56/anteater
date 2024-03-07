package cz.cvut.fit.anteater.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.service.SourcableService;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class SourcableController<T extends SourceableEntity> {
	private SourcableService<T> sourcableService;

	public SourcableController(SourcableService<T> sourcableService) {
		this.sourcableService = sourcableService;
	}

	@GetMapping
	public Page<T> search(@RequestParam(required = false) String name,
			@RequestParam(required = false) List<String> sources,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {
		return sourcableService.search(name, sources, PageRequest.of(page, size));
	}

	@GetMapping("/{id}")
	public T getById(@RequestParam String id) {
		return sourcableService.findById(id);
	}
}
