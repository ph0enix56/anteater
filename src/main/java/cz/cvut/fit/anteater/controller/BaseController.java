package cz.cvut.fit.anteater.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
		try { return baseService.findByNameAndSources(name, sources); }
		catch (IllegalArgumentException e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); }
	}	

	@GetMapping("/{id}")
	public T getById(@PathVariable String id) {
		try { return baseService.findById(id); }
		catch (NoSuchElementException e) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); }
		catch (IllegalArgumentException e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); }
	}
}
