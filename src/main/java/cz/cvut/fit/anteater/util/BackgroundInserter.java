package cz.cvut.fit.anteater.util;

import java.util.List;
import java.util.Set;

import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.TextFeature;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BackgroundInserter {
	private final BackgroundRepository backgroundRepository;
	private final LanguageRepository languageRepository;
	private final ToolRepository toolRepository;

	public void insert() {
		backgroundRepository.save(
			Background.builder()
			.name("Acolyte")
			.description("You have spent your life in the service of a temple to a specific god or pantheon of gods. You act as an intermediary between the realm of the holy and the mortal world, performing sacred rites and offering sacrifices in order to conduct worshipers into the presence of the divine.")
			.features(List.of(
				new TextFeature("Shelter of the Faithful", "As an acolyte, you command the respect of those who share your faith, and you can perform the religious ceremonies of your deity. You and your adventuring companions can expect to receive free healing and care at a temple, shrine, or other established presence of your faith, though you must provide any material components needed for spells. Those who share your religion will support you (but only you) at a modest lifestyle.")
			))
			.skillProficiencies(new BonusList<>(2, Set.of(
				Skill.insight,
				Skill.religion)
			))
			.languageProficiencies(new BonusList<>(2))
			.toolProficiencies(new BonusList<>())
			.build()
		);

		backgroundRepository.save(
			Background.builder()
			.name("Charlatan")
			.description("You have always had a way with people. You know what makes them tick, you can tease out their hearts' desires after a few minutes of conversation, and with a few leading questions you can read like they were children's books. It's a useful talent, and one that you're perfectly willing to use for your advantage.")
			.features(List.of(
				new TextFeature("False Identity", "You have created a second identity that includes documentation, established acquaintances, and disguises that allow you to assume that persona. Additionally, you can forge documents including official papers and personal letters, as long as you have seen an example of the kind of document or the handwriting you are trying to copy.")
			))
			.skillProficiencies(new BonusList<>(2, Set.of(
				Skill.deception,
				Skill.sleight_of_hand)
			))
			.languageProficiencies(new BonusList<>())
			.toolProficiencies(new BonusList<>(2, Set.of(
				//toolRepository.findByName("Disguise kit"),
				//toolRepository.findByName("Forgery kit")
			)))
			.build()
		);
	}
}
