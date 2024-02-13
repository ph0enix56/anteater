package cz.cvut.fit.anteater.model.dto;

import java.util.List;

import cz.cvut.fit.anteater.model.entity.Source;

public interface CharacterInfo {
	String getId();
	String getCharacterName();
	String getPlayerName();
	String getCardPhotoUrl();
	String getSheetPhotoUrl();
	Integer getLevel();

	List<Source> getSources();
	ClassInfo getDndClass();
	RaceInfo getRace();
	BackgroundInfo getBackground();
	String getSubclass();

	interface ClassInfo { String getId(); String getName(); }
	interface RaceInfo { String getId(); String getName(); }
	interface BackgroundInfo { String getId(); String getName(); }
}
