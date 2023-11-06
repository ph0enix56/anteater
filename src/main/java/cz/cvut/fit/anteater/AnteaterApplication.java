package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.entity.Background;
import cz.cvut.fit.anteater.entity.BonusList;
import cz.cvut.fit.anteater.entity.Language;
import cz.cvut.fit.anteater.entity.Source;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.util.LanguageInserter;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Autowired
	LanguageRepository languageRepository;

	@Override
	public void run(String... args) throws Exception {
		BonusList<Language> l = new BonusList<>();
		//Language l = new Language("Common", new Source("srd", "Sys ref"), null);
		//languageRepository.save(l);
		//LanguageInserter inserter = new LanguageInserter(languageRepository);
		//inserter.insert();
	}
}
