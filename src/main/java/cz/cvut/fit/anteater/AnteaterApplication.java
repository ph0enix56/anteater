package cz.cvut.fit.anteater;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import cz.cvut.fit.anteater.controller.BackgroundController;
import cz.cvut.fit.anteater.controller.ToolController;
import cz.cvut.fit.anteater.enumeration.ToolType;
import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;
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
	@Autowired SourceRepository sourceRepository;
	//@Autowired BackgroundController backgroundController;

	@Override
	public void run(String... args) throws Exception {
		//Source srd = Source.builder().abbreviation("SRD").name("System Reference Document").build();
		//Source exp1 = Source.builder().abbreviation("EXP1").name("Experimental 1").build();
		//Source exp2 = Source.builder().abbreviation("EXP2").name("Experimental 2").build();
		//Tool t1 = Tool.builder().name("Tinker's Tools").type(ToolType.artisan).source(srd).build();
		//Tool t2 = Tool.builder().name("Lute").type(ToolType.instrument).source(srd).build();
		//Tool t3 = Tool.builder().name("Thieves' Tools").type(ToolType.other).source(srd).build();
		//Tool t4 = Tool.builder().name("Cook's Utensils").type(ToolType.artisan).source(srd).build();
		//Tool e1 = Tool.builder().name("Experimental Tool 1").type(ToolType.other).source(exp1).build();
		//Tool e11 = Tool.builder().name("Experimental Tool 1.1").type(ToolType.other).source(exp1).build();
		//Tool e2 = Tool.builder().name("Experimental Tool 2").type(ToolType.other).source(exp2).build();
		//List<Tool> tools = Arrays.asList(t1, t2, t3, t4, e1, e11, e2);
		//sourceRepository.saveAll(Arrays.asList(srd, exp1, exp2));
		//toolRepository.saveAll(tools);
	}
}
