package cz.cvut.fit.anteater;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.entity.Background;
import cz.cvut.fit.anteater.entity.Language;
import cz.cvut.fit.anteater.entity.Tool;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.enumeration.ToolType;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {

	@Autowired
	private LanguageRepository languageRepo;

	@Autowired
	private ToolRepository toolRepo;

	@Autowired
	private BackgroundRepository backgroundRepo;

	//@Autowired
	//private RaceRepository raceRepo;

	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(languageRepo.findAll());
		System.out.println(backgroundRepo.findAll());

		//// insert simple data for each entity
		//// language
		//if (languageRepo.findByName("Common") == null) {
		//	Language common = new Language();
		//	common.setName("Common");
		//	common.setExotic(false);
		//	languageRepo.save(common);
		//}

		//// tool
		//if (toolRepo.findByName("Tinker's Tools") == null) {
		//	Tool tinker = new Tool();
		//	tinker.setName("Tinker's Tools");
		//	tinker.setType(ToolType.ARTISAN);
		//	toolRepo.save(tinker);
		//}

		//System.out.println("Artisan's: " + toolRepo.findByType(ToolType.ARTISAN).size());
		//System.out.println("Instruments: " + toolRepo.findByType(ToolType.INSTRUMENT).size());
		//System.out.println("Gaming: " + toolRepo.findByType(ToolType.GAMING).size());
		//System.out.println("Vehicles: " + toolRepo.findByType(ToolType.VEHICLE).size());
		//System.out.println("Other: " + toolRepo.findByType(ToolType.OTHER).size());

		//// background
		//Background acolyte = new Background();
		//acolyte.setName("Acolyte");
		//acolyte.setDescription("You have spent your life in the service of a temple to a specific god or pantheon of gods.");
		//acolyte.setFeature("Shelter of the Faithful");
		//BonusList<Skill> sp = new BonusList<Skill>();
		//sp.setAmount(2);
		//sp.setDefaults(Set.of(Skill.INSIGHT, Skill.RELIGION));
		//acolyte.setSkillProficiencies(sp);
		//BonusList<Language> lp = new BonusList<Language>();
		//lp.setAmount(2);
		//lp.setDefaults(Set.of());
		//acolyte.setLanguageProficiencies(lp);
		//BonusList<Tool> tp = new BonusList<Tool>();
		//tp.setAmount(0);
		//tp.setDefaults(Set.of());
		//acolyte.setToolProficiencies(tp);
		//backgroundRepo.save(acolyte);
	}
}
