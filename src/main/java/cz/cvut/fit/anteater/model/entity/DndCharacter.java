package cz.cvut.fit.anteater.model.entity;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Skill;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "character")
public class DndCharacter {
	@Id
	private String id;

	private String character_name;

	private String player_name;

	private String card_photo_url;

	private String sheet_photo_url;

	@DocumentReference
	private DndClass dnd_class;

	@DocumentReference
	private Race race;

	@DocumentReference
	private Background background;

	private Integer level;

	private HashMap<Ability, Integer> ability_scores;
	
	private HashSet<Skill> skills;

	private HashSet<Ability> saving_throws;
}


