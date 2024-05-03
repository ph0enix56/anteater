package cz.cvut.fit.anteater.model.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import cz.cvut.fit.anteater.constants.Constants;
import cz.cvut.fit.anteater.dto.request.AbilityInput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.value.Proficiency;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Document(collection = "character")
public class Character {

	@Id
	private String id;

	private String characterName;

	private String playerName;

	private String cardPhotoUrl;

	private String sheetPhotoUrl;

	private List<Source> sources;

	@Field("classId")
	@DocumentReference
	private DndClass dndClass;

	private String subclass;

	@Field("raceId")
	@DocumentReference
	private Race race;

	private Size size;

	@Field("backgroundId")
	@DocumentReference
	private Background background;

	private Integer level;

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