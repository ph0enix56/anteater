package cz.cvut.fit.anteater.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.service.BaseService;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class BaseController<T extends SourceableEntity> {
	private BaseService<T> baseService;

	public BaseController(BaseService<T> baseService) {
		this.baseService = baseService;
	}

	@GetMapping
	public Iterable<T> search(@RequestParam(required = false) String name, @RequestParam(required = false) List<String> sources) {
		return baseService.findByNameAndSources(name, sources);
	}	

	@GetMapping("/{id}")
	public T getById(@PathVariable String id) {
		return baseService.findById(id);
	}
}
