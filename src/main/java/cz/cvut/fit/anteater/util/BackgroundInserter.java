package cz.cvut.fit.anteater.util;

import java.util.List;
import java.util.Set;

import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.TextFeature;
import cz.cvut.fit.anteater.repository.BackgroundRepository;

public class BackgroundInserter {
	private BackgroundRepository backgroundRepository;

	public BackgroundInserter(BackgroundRepository backgroundRepository) {
		this.backgroundRepository = backgroundRepository;
	}

	//public void insert() {
	//	backgroundRepository.save(
	//		new Background("Acolyte",
	//		"You have spent your life in the service of a temple to a specific god or pantheon of gods. You act as an intermediary between the realm of the holy and the mortal world, performing sacred rites and offering sacrifices in order to conduct worshipers into the presence of the divine.",
	//		List.of(
	//			new TextFeature("Shelter of the Faithful", "As an acolyte, you command the respect of those who share your faith, and you can perform the religious ceremonies of your deity. You and your adventuring companions can expect to receive free healing and care at a temple, shrine, or other established presence of your faith, though you must provide any material components needed for spells. Those who share your religion will support you (but only you) at a modest lifestyle.")
	//		),
	//		new BonusList<>(2, Set.of(Skill.insight, Skill.religion)),
	//		new BonusList<>(2, Set.of()),
	//		new BonusList<>(0, Set.of())));
		
	//	backgroundRepository.save(
	//		new Background("Charlatan",
	//		"You have always had a way with people. You know what makes them tick, you can tease out their hearts' desires after a few minutes of conversation, and with a few leading questions you can read like they were children's books. It's a useful talent, and one that you're perfectly willing to use for your advantage.",
	//		List.of(
	//			new TextFeature("False Identity", "You have created a second identity that includes documentation, established acquaintances, and disguises that allow you to assume that persona. Additionally, you can forge documents including official papers and personal letters, as long as you have seen an example of the kind of document or the handwriting you are trying to copy.")
	//		),
	//		new BonusList<>(2, Set.of(Skill.deception, Skill.sleight_of_hand)),
	//		new BonusList<>(0, Set.of()),
	//		new BonusList<>(0, Set.of())));
	//}
}
