package cz.cvut.fit.anteater.dto.response;

import java.util.List;

import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CharacterPdfOutput {
	private CharacterInfo info;

	private CharacterStats stats;

	private List<AbilityPdfOutput> abilities;

	private List<SkillPdfOutput> skills;

	private List<SkillPdfOutput> savingThrows;

	private Armor armor;

	private List<AttackOutput> attacks;

	private SpellcastingOutput spellcasting;

	private ProficiencyList proficiencies;

	private List<TextFeature> features;
}
