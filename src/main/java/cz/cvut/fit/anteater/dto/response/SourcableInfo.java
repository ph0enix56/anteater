package cz.cvut.fit.anteater.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SourcableInfo {
	private String id;
	private String name;
	private String sourceId;
}
