package cz.cvut.fit.anteater;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.entity.AbilityBonus;
import cz.cvut.fit.anteater.entity.Race;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Language;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.repository.RaceRepository;

//import cz.cvut.fit.anteater.entity.Spell;
//import cz.cvut.fit.anteater.repository.SpellRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {

	//@Autowired
	//private SpellRepository spellRepo;

	@Autowired
	private RaceRepository raceRepo;

	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Race human = new Race(
		//	"Human",
		//	List.of(Size.MEDIUM),
		//	30,
		//	"In the reckonings of most worlds, humans are the youngest of the common races, late to arrive on the world scene and short-lived in comparison to dwarves, elves, and dragons. Perhaps it is because of their shorter lives that they strive to achieve as much as they can in the years they are given. Or maybe they feel they have something to prove to the elder races, and that's why they build their mighty empires on the foundation of conquest and trade. Whatever drives them, humans are the innovators, the achievers, and the pioneers of the worlds.",
		//	List.of(new AbilityBonus(Ability.STRENGTH, 1),
		//			new AbilityBonus(Ability.DEXTERITY, 1),
		//			new AbilityBonus(Ability.CONSTITUTION, 1),
		//			new AbilityBonus(Ability.INTELLIGENCE, 1),
		//			new AbilityBonus(Ability.WISDOM, 1),
		//			new AbilityBonus(Ability.CHARISMA, 1)),
		//	0,
		//	List.of(Language.COMMON),
		//	1);
		//if (raceRepo.findByName("Human").isEmpty()) raceRepo.save(human);

		System.out.println(raceRepo.findByName("Human"));
		System.out.println(raceRepo.findAll().stream().filter(r -> r.getSpeed() == 30).count());

		//System.out.println(spellRepo.findByName("Detect Magic"));

		//System.out.println(spellRepo.findAll().stream().filter(s -> s.getLevel() == 1).count());

		//List<Spell> acidSpells = spellRepo.findAll().stream()
		//	.filter(s -> s.getDamageType() != null && s.getDamageType().contains("acid"))
		//	.collect(Collectors.toList());
		//acidSpells.stream().forEach(s -> System.out.println(s.getName()));
	}
}
