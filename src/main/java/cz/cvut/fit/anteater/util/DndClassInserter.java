package cz.cvut.fit.anteater.util;

import java.util.List;
import java.util.Set;

import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.model.value.BonusList;
import cz.cvut.fit.anteater.model.value.Dice;
import cz.cvut.fit.anteater.model.value.TextFeature;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DndClassInserter {
	private final DndClassRepository dndClassRepository;
	private final ToolRepository toolRepository;

	public void insert() {
		dndClassRepository.save(
			DndClass.builder()
			.name("Barbarian")
			.description("A fierce warrior of primitive background who can enter a battle rage.")
			.hitDice(new Dice(12))
			.features(List.of(
				new TextFeature("Rage", "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action. While raging, you gain the following benefits if you aren't wearing heavy armor: [Paragraphed text would follow here.]", 1),
				new TextFeature("Unarmored Defense", "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier. You can use a shield and still gain this benefit.", 1),
				new TextFeature("Danger Sense", "At 2nd level, you gain an uncanny sense of when things nearby aren't as they should be, giving you an edge when you dodge away from danger. You have advantage on Dexterity saving throws against effects that you can see, such as traps and spells. To gain this benefit, you can't be blinded, deafened, or incapacitated.", 2),
				new TextFeature("Reckless Attack", "When you make your first attack on your turn, you can decide to attack recklessly. Doing so gives you advantage on melee weapon attack rolls using Strength during this turn, but attack rolls against you have advantage until your next turn.", 2),
				new TextFeature("Extra Attack", "Beginning at 5th level, you can attack twice, instead of once, whenever you take the Attack action on your turn.", 5),
				new TextFeature("Fast Movement", "Starting at 5th level, your speed increases by 10 feet while you aren't wearing heavy armor.", 5)
			))
			.subclasses(List.of(
				"Berserker",
				"Subclass 2",
				"Subclass 3",
				"Last Subclass"
			))
			.skillProficiencies(new BonusList<>(2))
			.toolProficiencies(new BonusList<>(0))
			.build()
		);

		dndClassRepository.save(
			DndClass.builder()
			.name("Rogue")
			.description("A scoundrel who uses stealth and trickery to overcome obstacles and enemies.")
			.hitDice(new Dice(8))
			.features(List.of(
				new TextFeature("Expertise", "At 1st level, choose two of your skill proficiencies, or one of your skill proficiencies and your proficiency with thieves' tools. Your proficiency bonus is doubled for any ability check you make that uses either of the chosen proficiencies. At 6th level, you can choose two more of your proficiencies (in skills or with thieves' tools) to gain this benefit.", 1),
				new TextFeature("Sneak Attack", "Beginning at 1st level, you know how to strike subtly and exploit a foe's distraction. Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll. The attack must use a finesse or a ranged weapon. [More text follows]", 1),
				new TextFeature("Thieves' Cant", "During your rogue training you learned thieves' cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation. Only another creature that knows thieves' cant understands such messages. It takes four times longer to convey such a message than it does to speak the same idea plainly. ...", 1),
				new TextFeature("Cunning Action", "Starting at 2nd level, your quick thinking and agility allow you to move and act quickly. You can take a bonus action on each of your turns in combat. This action can be used only to take the Dash, Disengage, or Hide action.", 2)
			))
			.subclasses(List.of(
				"Thief",
				"Another Subclass",
				"Yet Another One",
				"And the Last One"
			))
			.skillProficiencies(new BonusList<>(4))
			.toolProficiencies(new BonusList<>(1, Set.of(
				toolRepository.findByName("Thieves' tools")
			)))
			.build()
		);
	}
}
