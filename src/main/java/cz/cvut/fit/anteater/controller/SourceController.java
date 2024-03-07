package cz.cvut.fit.anteater.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.repository.SourceRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/sources")
public class SourceController {
	private SourceRepository repository;

	public SourceController(SourceRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/{id}")
	public Source findById(@PathVariable String id) {
		try {
			if (id == null) throw new IllegalArgumentException("ID must not be null");
			return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping
	public List<Source> findAll() {
		try {
			return repository.findAll();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
