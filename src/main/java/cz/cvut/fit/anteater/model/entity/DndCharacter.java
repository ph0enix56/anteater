package cz.cvut.fit.anteater.model.entity;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

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

	@Field("character_name")
	private String characterName;

	@Field("player_name")
	private String playerName;

	@Field("card_photo_url")
	private String cardPhotoUrl;

	@Field("sheet_photo_url")
	private String sheetPhotoUrl;

	@Field("class")
	@DocumentReference
	private DndClass dndClass;

	@DocumentReference
	private Race race;

	@DocumentReference
	private Background background;

	private Integer level;

	@Field("ability_scores")
	private HashMap<Ability, Integer> abilities;
	
	private HashSet<Skill> skills;

	@Field("saving_throws")
	private HashSet<Ability> saves;
}
