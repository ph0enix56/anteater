package cz.cvut.fit.anteater.model.dto;

public interface CharacterInfo {

	String getId();
	String getCharacterName();
	String getPlayerName();
	String getCardPhotoUrl();
	String getSheetPhotoUrl();
	Integer getLevel();

	ClassInfo getDndClass();
	RaceInfo getRace();
	BackgroundInfo getBackground();

	interface ClassInfo { String getId(); String getName(); }
	interface RaceInfo { String getId(); String getName(); }
	interface BackgroundInfo { String getId(); String getName(); }
}
