package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.business.CharacterPDFExporter;
import cz.cvut.fit.anteater.business.CharacterService;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Autowired CharacterService characterService;

	@Override
	public void run(String... args) throws Exception {
		CharacterPDFExporter exporter = new CharacterPDFExporter();
		exporter.exportToPDF(characterService.getCharacterForPDF("65f38cb13d0e4e50aedebda8"), "assets/sheet3.pdf");
	}
}
