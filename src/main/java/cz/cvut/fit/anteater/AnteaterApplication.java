package cz.cvut.fit.anteater;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.entity.Spell;
import cz.cvut.fit.anteater.repository.SpellRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {

	@Autowired
	private SpellRepository spellRepo;

	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(spellRepo.findByName("Detect Magic"));

		System.out.println(spellRepo.findAll().stream().filter(s -> s.getLevel() == 1).count());

		List<Spell> acidSpells = spellRepo.findAll().stream()
			.filter(s -> s.getDamageType() != null && s.getDamageType().contains("acid"))
			.collect(Collectors.toList());
		acidSpells.stream().forEach(s -> System.out.println(s.getName()));
	}
}
