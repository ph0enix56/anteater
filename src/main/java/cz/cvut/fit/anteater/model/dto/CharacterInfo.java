package cz.cvut.fit.anteater.model.dto;

import java.util.List;

public interface CharacterInfo {
	String getId();
	String getCharacterName();
	String getPlayerName();
	String getCardPhotoUrl();
	String getSheetPhotoUrl();
	Integer getLevel();

	List<ShortSource> getSources();
	ClassInfo getDndClass();
	RaceInfo getRace();
	BackgroundInfo getBackground();

	interface ShortSource { String getId(); }
	interface ClassInfo { String getId(); String getName(); }
	interface RaceInfo { String getId(); String getName(); }
	interface BackgroundInfo { String getId(); String getName(); }
}
