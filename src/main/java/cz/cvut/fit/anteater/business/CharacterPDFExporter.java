package cz.cvut.fit.anteater.business;

import java.io.File;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import cz.cvut.fit.anteater.dto.response.AbilityOutput;
import cz.cvut.fit.anteater.dto.response.AttackOutput;
import cz.cvut.fit.anteater.dto.response.CharacterComplete;
import cz.cvut.fit.anteater.dto.response.ProficiencyList;
import cz.cvut.fit.anteater.dto.response.SkillOutput;
import cz.cvut.fit.anteater.dto.response.SpellcastingOutput;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.value.Spellcasting;
import cz.cvut.fit.anteater.model.value.TextFeature;

public class CharacterPDFExporter {
	private String serializeProficiencies(ProficiencyList profs) {
		StringBuilder sb = new StringBuilder().append("Armor: ");
		String armorProfs = String.join(", ", profs.getArmor());
		sb.append(armorProfs);
		sb.append("\nWeapons: ");
		String weaponProfs = String.join(", ", profs.getWeapons());
		sb.append(weaponProfs);
		sb.append("\nTools: ");
		String toolProfs = String.join(", ", profs.getTools());
		sb.append(toolProfs);
		sb.append("\nLanguages: ");
		String langProfs = String.join(", ", profs.getLanguages());
		sb.append(langProfs);
		return sb.toString();
	}

	private String serializeFeatures(List<TextFeature> features) {
		StringBuilder sb = new StringBuilder();
		for (TextFeature f : features) {
			sb.append(f.getTitle());
			sb.append(". ");
			sb.append(f.getText());
			sb.append("\n");
		}
		return sb.toString();
	}

	public void exportToPDF(CharacterComplete ch, String path) {
		try (PDDocument doc = Loader.loadPDF(new File(path))) {
			PDAcroForm form = doc.getDocumentCatalog().getAcroForm();
			form.getField("characterName").setValue(ch.getInfo().getCharacterName());
			form.getField("classAndLevel").setValue(ch.getInfo().getDndClass().getName() + " " + ch.getInfo().getLevel());
			form.getField("background").setValue(ch.getInfo().getBackground().getName());
			form.getField("playerName").setValue(ch.getInfo().getPlayerName());
			form.getField("race").setValue(ch.getInfo().getRace().getName());
			for (AbilityOutput a : ch.getAbilities()) {
				form.getField(a.getLabel() + "Mod").setValue(a.getModifier().toString());
				form.getField(a.getLabel() + "Score").setValue(a.getScore().toString());
			}
			for (SkillOutput a : ch.getSavingThrows()) {
				form.getField(a.getLabel().toString() + "SaveMod").setValue(a.getModifier().toString());
				form.getField(a.getLabel().toString() + "SaveProf").setValue(a.getProficient() ? "Yes" : "Off");
			}
			for (SkillOutput a : ch.getSkills()) {
				form.getField(a.getLabel().toString() + "Mod").setValue(a.getModifier().toString());
				form.getField(a.getLabel().toString() + "Prof").setValue(a.getProficient() ? "Yes" : "Off");
			}
			form.getField("armorClass").setValue(ch.getStats().getArmorClass().toString());
			form.getField("initiative").setValue(ch.getStats().getInitiative().toString());
			form.getField("speed").setValue(ch.getStats().getSpeed().toString());
			form.getField("hpMax").setValue(ch.getStats().getHitPoints().toString());
			form.getField("hpCurrent").setValue(ch.getStats().getHitPoints().toString());
			form.getField("hdTotal").setValue(ch.getStats().getHitDice().toString());
			form.getField("hdCurrent").setValue(ch.getStats().getHitDice().toString());
			List<AttackOutput> attacks = ch.getAttacks();
			for (int i = 0; i < 3; i++) {
				form.getField("attack" + i + "Name").setValue(i < attacks.size() ? attacks.get(i).getName() : "");
				form.getField("attack" + i + "Bonus").setValue(i < attacks.size() ? attacks.get(i).getAttackBonus().toString() : "");
				form.getField("attack" + i + "Damage").setValue(i < attacks.size() ? attacks.get(i).getDamage() : "");
			}

			Armor armor = ch.getArmor();
			if (armor != null) {
				StringBuilder sb = new StringBuilder().append("Armor: ");
				sb.append(ch.getArmor().getName());
				if (armor.getStealthDisadvantage()) sb.append(" (Stealth Disadv.)");
				form.getField("equipment").setValue(sb.toString());
			}

			form.getField("proficiencies").setValue(serializeProficiencies(ch.getProficiencies()));
			form.getField("features").setValue(serializeFeatures(ch.getFeatures()));

			SpellcastingOutput sc = ch.getSpellcasting();
			if (sc != null) {
				form.getField("spellClass").setValue(ch.getInfo().getDndClass().getName());
				form.getField("spellAbility").setValue(sc.getAbilityAbbreviation());
				form.getField("spellSaveDc").setValue(sc.getSaveDc().toString());
				form.getField("spellAttackBonus").setValue(sc.getModifier().toString());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
