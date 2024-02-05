package cz.cvut.fit.anteater.model.dto;

import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.model.entity.Race;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInfo {
	private String id;

	private String character_name;

	private String player_name;

	private String card_photo_url;

	private String sheet_photo_url;

	private DndClass dnd_class;

	private Race race;

	private Background background;

	private Integer level;	
}
