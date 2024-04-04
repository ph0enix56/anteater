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

import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.repository.SourceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * REST controller for handling requests related to {@link Source} entities.
 * Provides endpoints for retrieving all sources and a single source by its ID.
 * 
 * @see Source
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.BASE_API_URL + "sources")
public class SourceController {
	private SourceRepository repository;

	public SourceController(SourceRepository repository) {
		this.repository = repository;
	}

	@Operation(summary = "Get source by ID", description = "Get a source by its ID.")
	@ApiResponse(responseCode = "200", description = "Source found and returned.")
	@ApiResponse(responseCode = "404", description = "Source with given ID not found.")
	@GetMapping("/{id}")
	public Source findById(@PathVariable @Parameter(description = "ID of source to return") String id) {
		try {
			if (id == null) throw new IllegalArgumentException("ID must not be null");
			return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@Operation(summary = "Get all sources", description = "Get all sources.")
	@ApiResponse(responseCode = "200", description = "All sources found and returned.")
	@GetMapping
	public List<Source> findAll() {
		try {
			return repository.findAll();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
