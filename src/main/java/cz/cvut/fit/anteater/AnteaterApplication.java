package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.LanguageRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Autowired
	LanguageRepository languageRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Language l = languageRepository.findByName("Common");
		System.out.println(l.getId());
		System.out.println(l.getName());
		System.out.println(l.getExotic());
		Language l2 = Language.builder().name("Test").exotic(false).build();
		languageRepository.save(l2);
		//Language l = new Language("Common", new Source("srd", "Sys ref"), null);
		//languageRepository.save(l);
		//LanguageInserter inserter = new LanguageInserter(languageRepository);
		//inserter.insert();
	}
}
