package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.entity.Language;
import cz.cvut.fit.anteater.entity.Tool;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;

public class Inserter {
	private BackgroundRepository backgroundRepository;
	private LanguageRepository languageRepository;
	private DndClassRepository dndClassRepository;
	private SourceRepository sourceRepository;
	private RaceRepository raceRepository;
	private ToolRepository toolRepository;

	public Inserter(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public void insertLangs() {
		Language langos = new Language();
		langos.setName("Common");
		langos.setExotic(false);
		languageRepository.save(langos);
	}
}
