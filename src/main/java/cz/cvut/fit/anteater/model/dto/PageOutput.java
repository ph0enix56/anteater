package cz.cvut.fit.anteater.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageOutput<T> {

	private T content;

	private Integer page;

	private Integer size;

	private Integer totalPages;

	private Integer totalElements;
}
