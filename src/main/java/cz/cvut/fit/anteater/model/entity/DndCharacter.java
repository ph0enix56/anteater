package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.dto.request.AbilityInput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.value.Proficiency;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
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

	private List<Source> sources;

	@Field("class")
	@DocumentReference
	private DndClass dndClass;

	private String subclass;

	@DocumentReference
	private Race race;

	private Size size;

	@DocumentReference
	private Background background;

	private Integer level;

	@Field("ability_scores")
	private Map<Ability, AbilityInput> abilities;

	private Set<Skill> skills;

	private List<Proficiency<Tool>> tools;

	private List<Proficiency<Language>> languages;

	private Armor armor;

	private List<Weapon> weapons;

	private List<Spell> spells;

	public Armor getArmor() {
		if (armor != null) return armor;
		if (dndClass != null && dndClass.getDefaultArmor() != null) return dndClass.getDefaultArmor();
		return Constants.NO_ARMOR_DEFAULT;
	}
}
