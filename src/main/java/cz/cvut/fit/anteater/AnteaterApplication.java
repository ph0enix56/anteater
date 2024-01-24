package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.controller.BackgroundController;
import cz.cvut.fit.anteater.controller.ToolController;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Autowired LanguageRepository languageRepository;
	@Autowired ToolRepository toolRepository;
	@Autowired DndClassRepository dndClassRepository;
	@Autowired BackgroundRepository backgroundRepository;
	@Autowired RaceRepository raceRepository;
	@Autowired ToolController toolController;
	@Autowired BackgroundController backgroundController;

	@Override
	public void run(String... args) throws Exception {
		//LanguageInserter languageInserter = new LanguageInserter(languageRepository);
		//languageInserter.insert();
		//ToolInserter toolInserter = new ToolInserter(toolRepository);
		//toolInserter.insert();
		//DndClassInserter dndClassInserter = new DndClassInserter(dndClassRepository, toolRepository);
		//dndClassInserter.insert();
		//BackgroundInserter backgroundInserter = new BackgroundInserter(backgroundRepository, languageRepository, toolRepository);
		//backgroundInserter.insert();
		//RaceInserter raceInserter = new RaceInserter(raceRepository, languageRepository);
		//raceInserter.insert();
	}
}
