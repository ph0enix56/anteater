package cz.cvut.fit.anteater.controller.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * REST controller base for retrieving content of the {@link SourceableEntity} type. Provides
 * paginated search and retrieval by ID endpoints, but can be extended with more specific ones
 * as appropriate for the entity type. To be used as a base to extend for controllers
 * handling entities of the appropriate type, otherwise no actual endpoint mappings are created.
 * 
 * @param <T> type of entity to be controlled
 * @see SourceableEntity
 */
@CrossOrigin(origins = Constants.FRONTEND_URL)
public abstract class SourcableBaseController<T extends SourceableEntity> {
	private SourcableBaseService<T> sourcableService;

	public SourcableBaseController(SourcableBaseService<T> sourcableService) {
		this.sourcableService = sourcableService;
	}

	@Operation(summary = "Search for content",
		description = "Get all content of given type with optional filtering by name and source. " +
		"Uses paginated interface. If no page and size are provided, all results are returned on one page. If only one of them is provided, " +
		"the paginated default specified below is used for the other. If both are provided, the specified values are used.")
	@ApiResponse(responseCode = "200", description = "Content found and page returned (possibly empty in case of no matching results).")
	@GetMapping
	public Page<T> search(
			@RequestParam(required = false) @Parameter(description = "name to search for with substring matching, meaning content " +
			"with name containing this string will be returned") String name,
			@RequestParam(required = false) @Parameter(description = "list of source IDs (abbreviations) to filter by, meaning content " +
			"with source ID in this list will be returned") List<String> source,
			@RequestParam(required = false) @Parameter(description = "page number to return, 0-based (paginated default: 0)") Integer page,
			@RequestParam(required = false) @Parameter(description = "number of results per page (paginated default: 10)") Integer size) {
		return sourcableService.search(name, source, page, size);
	}

	@Operation(summary = "Get content by ID", description = "Get a single content object by its ID.")
	@ApiResponse(responseCode = "200", description = "Content found and returned.")
	@ApiResponse(responseCode = "404", description = "Content with given ID not found.")
	@GetMapping("/{id}")
	public T getById(@PathVariable @Parameter(description = "ID of content to return") String id) {
		return sourcableService.findById(id);
	}
}
