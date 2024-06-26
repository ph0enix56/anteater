package cz.cvut.fit.anteater.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProficiencyList {
	private List<String> armor;
	private List<String> weapons;
	private List<String> tools;
	private List<String> languages;
}
