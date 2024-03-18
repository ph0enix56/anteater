package cz.cvut.fit.anteater;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.business.CharacterPDFExporter;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CharacterPDFExporter exporter = new CharacterPDFExporter();
		exporter.exportToPDF(null, "assets/test.pdf");
	}
}
