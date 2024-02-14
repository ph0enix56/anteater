package cz.cvut.fit.anteater.model.dto;

import java.util.HashMap;
import java.util.HashSet;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Armor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInput {
	private String id;

	@NotEmpty
	private String characterName;

	@NotEmpty
	private String playerName;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;

	@JsonProperty("class")
	@NotEmpty
	private String dndClass;

	@NotEmpty
	private String race;

	@NotEmpty
	private String background;

	private String subclass;

	@NotNull
	@Range(min = 1, max = 20)
	private Integer level;

	@NotNull
	@Size(min = 6, max = 6)
	// this kinda works by being a map and the enum only having 6 values, but maybe could be done better
	private HashMap<Ability, @Range(min = 1, max = 20) Integer> abilities;

	private HashSet<Skill> skillProficiencies;

	private HashSet<Ability> saveProficiencies;

	private Armor armor;
}
