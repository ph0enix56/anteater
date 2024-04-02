package cz.cvut.fit.anteater.business;

import java.io.File;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.springframework.stereotype.Component;

import cz.cvut.fit.anteater.dto.pdf.AbilityPdfOutput;
import cz.cvut.fit.anteater.dto.pdf.CharacterPdfOutput;
import cz.cvut.fit.anteater.dto.pdf.SkillPdfOutput;
import cz.cvut.fit.anteater.dto.response.AttackOutput;
import cz.cvut.fit.anteater.dto.response.ProficiencyList;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.value.TextFeature;

@Component
public class CharacterPDFExporter {
	private String serializeProficiencies(ProficiencyList profs) {
		StringBuilder sb = new StringBuilder().append("Armor: ");
		String armorProfs = String.join(", ", profs.getArmor());
		sb.append(armorProfs);
		sb.append("\n\nWeapons: ");
		String weaponProfs = String.join(", ", profs.getWeapons());
		sb.append(weaponProfs);
		sb.append("\n\nTools: ");
		String toolProfs = String.join(", ", profs.getTools());
		sb.append(toolProfs);
		sb.append("\n\nLanguages: ");
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

	private String asModifier(Integer i) {
		if (i >= 0) return "+" + i;
		return i.toString();
	}

	public void exportToPDF(CharacterPdfOutput ch, String outputPath, File pdfTemplate) {
		try (PDDocument doc = Loader.loadPDF(pdfTemplate)) {
			PDAcroForm form = doc.getDocumentCatalog().getAcroForm();
			form.getField("characterName").setValue(ch.getInfo().getCharacterName());
			form.getField("classAndLevel").setValue(ch.getInfo().getDndClass().getName() + " " + ch.getInfo().getLevel());
			form.getField("background").setValue(ch.getInfo().getBackground().getName());
			form.getField("playerName").setValue(ch.getInfo().getPlayerName());
			form.getField("race").setValue(ch.getInfo().getRace().getName());
			for (AbilityPdfOutput a : ch.getAbilities()) {
				form.getField(a.getAbbreviation() + "Mod").setValue(asModifier(a.getModifier()));
				form.getField(a.getAbbreviation() + "Score").setValue(a.getScore().toString());
			}
			for (SkillPdfOutput s : ch.getSavingThrows()) {
				form.getField(s.getAbbreviation() + "SaveMod").setValue(asModifier(s.getModifier()));
				form.getField(s.getAbbreviation() + "SaveProf").setValue(s.getProficient() ? "Yes" : "Off");
			}
			for (SkillPdfOutput s : ch.getSkills()) {
				form.getField(s.getAbbreviation() + "Mod").setValue(asModifier(s.getModifier()));
				form.getField(s.getAbbreviation() + "Prof").setValue(s.getProficient() ? "Yes" : "Off");
			}
			form.getField("proficiencyBonus").setValue(asModifier(ch.getStats().getProficiencyBonus()));
			form.getField("armorClass").setValue(ch.getStats().getArmorClass().toString());
			form.getField("initiative").setValue(asModifier(ch.getStats().getInitiative()));
			form.getField("speed").setValue(ch.getStats().getSpeed().toString());
			form.getField("hpMax").setValue(ch.getStats().getHitPoints().toString());
			form.getField("hdMax").setValue(ch.getStats().getHitDice().getNotation());

			List<AttackOutput> attacks = ch.getAttacks();
			for (int i = 0; i < 4; i++) {
				form.getField("atk" + (i + 1) + "name").setValue(i < attacks.size() ? attacks.get(i).getName() : "");
				form.getField("atk" + (i + 1) + "bonus").setValue(i < attacks.size() ? asModifier(attacks.get(i).getAttackBonus()) : "");
				form.getField("atk" + (i + 1) + "damage").setValue(i < attacks.size() ? attacks.get(i).getDamage() : "");
			}

			Armor armor = ch.getArmor();
			if (armor != null) {
				StringBuilder sb = new StringBuilder().append(ch.getArmor().getName());
				if (armor.getStealthDisadvantage()) sb.append(" (Stealth Disadv.)");
				form.getField("armor").setValue(sb.toString());
			}

			form.getField("proficiencies").setValue(serializeProficiencies(ch.getProficiencies()));
			form.getField("features").setValue(serializeFeatures(ch.getFeatures()));

			doc.save(outputPath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to export character to PDF", e);
		}
	}
}
