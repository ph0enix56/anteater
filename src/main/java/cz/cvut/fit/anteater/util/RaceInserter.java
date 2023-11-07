package cz.cvut.fit.anteater.util;

import java.util.List;
import java.util.Set;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.Size;
import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.TextFeature;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RaceInserter {
	private final RaceRepository raceRepository;
	private final LanguageRepository languageRepository;

	public void insert() {
		raceRepository.save(
			Race.builder()
			.name("Half-Elf")
			.description("Walking in two worlds but truly belonging to neither, half-elves combine what some say are the best qualities of their elf and human parents: human curiosity, inventiveness, and ambition tempered by the refined senses, love of nature, and artistic tastes of the elves. Some half-elves live among humans, set apart by their emotional and physical differences, watching friends and loved ones age while time barely touches them. Others live with the elves, growing to adulthood while their peers continue to live as children, growing restless in the timeless elven realms.")
			.features(List.of(
				new TextFeature("Darkvision", "Thanks to your elf blood, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."),
				new TextFeature("Fey Ancestry", "You have advantage on saving throws against being charmed, and magic can't put you to sleep.")
			))
			.speed(30)
			.sizeOptions(Set.of(Size.medium))
			.abilityScoresPlus2(new BonusList<>(1, Set.of(Ability.charisma)))
			.abilityScoresPlus1(new BonusList<>(2))
			.skillProficiencies(new BonusList<>(2))
			.languageProficiencies(new BonusList<>(3, Set.of(
				languageRepository.findByName("Common"),
				languageRepository.findByName("Elvish")
			)))
			.build()
		);

		raceRepository.save(
			Race.builder()
				.name("Human")
				.description("In the reckonings of most worlds, humans are the youngest of the common races, late to arrive on the world scene and short-lived in comparison to dwarves, elves, and dragons. Perhaps it is because of their shorter lives that they strive to achieve as much as they can in the years they are given. Or maybe they feel they have something to prove to the elder races, and that's why they build their mighty empires on the foundation of conquest and trade. Whatever drives them, humans are the innovators, the achievers, and the pioneers of the worlds.")
				.features(List.of())
				.speed(30)
				.sizeOptions(Set.of(Size.medium))
				.abilityScoresPlus2(new BonusList<>(0))
				.abilityScoresPlus1(new BonusList<>(6, Set.of(
					Ability.strength,
					Ability.dexterity,
					Ability.constitution,
					Ability.intelligence,
					Ability.wisdom,
					Ability.charisma
				)))
				.skillProficiencies(new BonusList<>())
				.languageProficiencies(new BonusList<>(2, Set.of(languageRepository.findByName("Common"))))
				.build()
		);
		

		raceRepository.save(
    	Race.builder()
			.name("Gnome (Forest)")
			.description("A constant hum of busy activity pervades the warrens and neighborhoods where gnomes form their close-knit communities. Louder sounds punctuate the hum: a crunch of grinding gears here, a minor explosion there, a yelp of surprise or triumph, and especially bursts of laughter. Gnomes take delight in life, enjoying every moment of invention, exploration, investigation, creation, and play.")
			.features(List.of(
				new TextFeature("Darkvision", "Accustomed to life underground, you can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can't discern color in darkness, only shades of gray."),
				new TextFeature("Gnome Cunning", "You have advantage on all Intelligence, Wisdom, and Charisma saving throws against magic."),
				new TextFeature("Natural Illusionist", "You know the minor illusion cantrip. Intelligence is your spellcasting ability for it."),
				new TextFeature("Speak with Small Beasts", "Through sounds and gestures, you can communicate simple ideas with Small or smaller beasts. Forest gnomes love animals and often keep squirrels, badgers, rabbits, moles, woodpeckers, and other creatures as beloved pets.")
			))
			.speed(25)
			.sizeOptions(Set.of(Size.small))
			.abilityScoresPlus2(new BonusList<>(1, Set.of(Ability.dexterity)))
			.abilityScoresPlus1(new BonusList<>(1, Set.of(Ability.intelligence)))
			.skillProficiencies(new BonusList<>(0))
			.languageProficiencies(new BonusList<>(2, Set.of(languageRepository.findByName("Common"), languageRepository.findByName("Gnomish"))))
			.build()
		);
	}
}
