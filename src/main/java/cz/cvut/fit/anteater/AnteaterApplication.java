package cz.cvut.fit.anteater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.entity.BonusList;
import cz.cvut.fit.anteater.entity.Language;
import cz.cvut.fit.anteater.entity.Race;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private LanguageRepository languageRepository;

	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Race r = new Race();
		r.setName("Half-Elf");
		r.setDescription("Walking in two worlds but truly belonging to neither, half-elves combine what some say are the best qualities of their elf and human parents: human curiosity, inventiveness, and ambition tempered by the refined senses, love of nature, and artistic tastes of the elves. Some half-elves live among humans, set apart by their emotional and physical differences, watching friends and loved ones age while time barely touches them. Others live with the elves, growing to adulthood while their peers continue to live as children, growing restless in the timeless elven realms. Many half-elves, unable to fit into either society, choose lives of solitary wandering or join with other misfits and outcasts in the adventuring life.");
		r.setFeatures(new ArrayList<String>());
		r.setSpeed(30);
		r.setSizeOptions(new HashSet<>(Arrays.asList(Size.medium)));

		BonusList<Ability> as1 = new BonusList<Ability>();
		as1.setAmount(2);
		as1.setDefaults(new HashSet<>());
		r.setAbilityScoresPlus1(as1);

		BonusList<Ability> as2 = new BonusList<Ability>();
		as2.setAmount(1);
		as2.setDefaults(new HashSet<>(Arrays.asList(Ability.charisma)));
		r.setAbilityScoresPlus2(as2);

		BonusList<Skill> sp = new BonusList<Skill>();
		sp.setAmount(2);
		sp.setDefaults(new HashSet<>());
		r.setSkillProficiencies(sp);

		BonusList<Language> lp = new BonusList<Language>();
		lp.setAmount(3);
		lp.setDefaults(new HashSet<>(Arrays.asList(languageRepository.findByName("Common"), languageRepository.findByName("Elvish"))));
		r.setLanguageProficiencies(lp);

		raceRepository.save(r);
	}
}
