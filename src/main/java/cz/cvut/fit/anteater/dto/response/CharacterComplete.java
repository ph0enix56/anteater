package cz.cvut.fit.anteater.dto.response;

import java.util.List;

import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.value.Proficiency;
import cz.cvut.fit.anteater.model.value.TextFeature;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterComplete {
	private String id;

	private CharacterInfo info;

	private CharacterStats stats;

	private List<Source> sources;

	private List<AbilityOutput> abilities;

	private List<SkillOutput> skills;

	private List<SkillOutput> savingThrows;

	private Boolean armorEquipped;

	private Armor armor;

	private List<AttackOutput> attacks;

	private SpellcastingOutput spellcasting;

	private ProficiencyList proficiencies;

	private List<Proficiency<Tool>> tools;

	private List<Proficiency<Language>> languages;

	private List<TextFeature> features;
}
