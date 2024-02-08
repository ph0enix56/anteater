package cz.cvut.fit.anteater.model.dto;

import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInput {
	private String id;
	
	private String characterName;
	
	private String playerName;
	
	private String cardPhotoUrl;

	private String sheetPhotoUrl;

	@JsonProperty("class")
	private String dndClass;

	private String race;

	private String background;

	private Integer level;

	private HashMap<Ability, Integer> abilityScores;

	private HashSet<Skill> skills;

	private HashSet<Ability> savingThrows;
}
