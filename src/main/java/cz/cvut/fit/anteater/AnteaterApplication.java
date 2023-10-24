package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.util.Inserter;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Autowired
	LanguageRepository languageRepository;

	@Override
	public void run(String... args) throws Exception {
		Inserter inserter = new Inserter(languageRepository);
		inserter.insertLangs();
	}
}
