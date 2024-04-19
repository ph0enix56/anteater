package cz.cvut.fit.anteater.business;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.mapping.CharacterMapper;
import cz.cvut.fit.anteater.dto.request.AbilityInput;
import cz.cvut.fit.anteater.dto.request.CharacterInput;
import cz.cvut.fit.anteater.dto.request.SkillInput;
import cz.cvut.fit.anteater.dto.response.AttackOutput;
import cz.cvut.fit.anteater.dto.response.CharacterComplete;
import cz.cvut.fit.anteater.dto.response.CharacterShort;
import cz.cvut.fit.anteater.dto.response.SkillOutput;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ProficiencySource;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.value.Proficiency;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;
import cz.cvut.fit.anteater.repository.sourcable.ArmorRepository;
import cz.cvut.fit.anteater.repository.sourcable.BackgroundRepository;
import cz.cvut.fit.anteater.repository.sourcable.DndClassRepository;
import cz.cvut.fit.anteater.repository.sourcable.LanguageRepository;
import cz.cvut.fit.anteater.repository.sourcable.RaceRepository;
import cz.cvut.fit.anteater.repository.sourcable.SpellRepository;
import cz.cvut.fit.anteater.repository.sourcable.ToolRepository;
import cz.cvut.fit.anteater.repository.sourcable.WeaponRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repo;
	private SourceRepository sourceRepo;
	private DndClassRepository classRepo;
	private RaceRepository raceRepo;
	private BackgroundRepository backgroundRepo;
	private ToolRepository toolRepo;
	private LanguageRepository languageRepo;
	private WeaponRepository weaponRepo;
	private SpellRepository spellRepo;
	private ArmorRepository armorRepo;
	private CharacterMapper mapper;
	private CharacterPDFExporter pdfExporter;

	public CharacterService(DndCharacterRepository repository, SourceRepository sourceRepository,
			DndClassRepository classRepository,	RaceRepository raceRepository,
			BackgroundRepository backgroundRepository, ToolRepository toolRepository,
			LanguageRepository languageRepository, WeaponRepository weaponRepository,
			SpellRepository spellRepository, ArmorRepository armorRepository,
			CharacterMapper mapper, CharacterPDFExporter pdfExporter) {
		this.repo = repository;
		this.sourceRepo = sourceRepository;
		this.classRepo = classRepository;
		this.raceRepo = raceRepository;
		this.backgroundRepo = backgroundRepository;
		this.toolRepo = toolRepository;
		this.languageRepo = languageRepository;
		this.weaponRepo = weaponRepository;
		this.spellRepo = spellRepository;
		this.armorRepo = armorRepository;
		this.mapper = mapper;
		this.pdfExporter = pdfExporter;
	}

	public List<CharacterShort> getAllCharacters() {
		return repo.findAll().stream().map(mapper::toShort).toList();
	}

	public CharacterComplete getCompleteCharacter(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		return mapper.toComplete(c);
	}

	public Resource getPdf(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		String outputPath = Constants.PDF_EXPORT_DIRECTORY + c.getId() + ".pdf";
		pdfExporter.exportToPDF(mapper.toPdfOutput(c), outputPath, Constants.PDF_TEMPLATE_FILE);
		try {
			return new UrlResource(Paths.get(outputPath).toUri());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Failed to load PDF file");
		}
	}

	public CharacterComplete saveCharacter(CharacterInput in, Boolean isCreate) {
		if (in == null) throw new IllegalArgumentException("Entity cannot be null");
		DndClass dndClass = classRepo.findById(in.getDndClass().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid class id"));
		Race race = raceRepo.findById(in.getRace().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid race id"));
		Background background = backgroundRepo.findById(in.getBackground().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid background id"));
		var builder = DndCharacter.builder()
			.characterName(in.getInfo().getCharacterName())
			.playerName(in.getInfo().getPlayerName())
			.cardPhotoUrl(in.getInfo().getCardPhotoUrl())
			.sheetPhotoUrl(in.getInfo().getSheetPhotoUrl())
			.sources(in.getInfo().getSourceIds().stream().map(id -> sourceRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid source id"))).toList())
			.dndClass(dndClass)
			.subclass(in.getDndClass().getSubclass())
			.race(race)
			.size(in.getRace().getSize())
			.background(background);

		if (in.getBackground().getToolIds().size() > background.getToolProficiencies().getAmount()) {
			throw new IllegalArgumentException("Too many background tool proficiencies");
		}
		if (in.getDndClass().getToolIds().size() > dndClass.getToolProficiencies().getAmount()) {
			throw new IllegalArgumentException("Too many class tool proficiencies");
		}
		if (in.getBackground().getLanguageIds().size() > background.getLanguageProficiencies().getAmount()) {
			throw new IllegalArgumentException("Too many background language proficiencies");
		}
		if (in.getRace().getLanguageIds().size() > race.getLanguageProficiencies().getAmount()) {
			throw new IllegalArgumentException("Too many race language proficiencies");
		}

		List<Proficiency<Tool>> toolProf = new ArrayList<>();
		for (String tid : in.getBackground().getToolIds()) {
			Tool t = toolRepo.findById(tid).orElseThrow(() -> new IllegalArgumentException("Invalid tool id"));
			toolProf.add(new Proficiency<Tool>(t, ProficiencySource.background));
		}
		for (String tid : in.getDndClass().getToolIds()) {
			Tool t = toolRepo.findById(tid).orElseThrow(() -> new IllegalArgumentException("Invalid tool id"));
			toolProf.add(new Proficiency<Tool>(t, ProficiencySource.dndClass));
		}
		builder.tools(toolProf);

		List<Proficiency<Language>> langProf = new ArrayList<>();
		for (String lid : in.getBackground().getLanguageIds()) {
			Language l = languageRepo.findById(lid).orElseThrow(() -> new IllegalArgumentException("Invalid language id"));
			langProf.add(new Proficiency<Language>(l, ProficiencySource.background));
		}
		for (String lid : in.getRace().getLanguageIds()) {
			Language l = languageRepo.findById(lid).orElseThrow(() -> new IllegalArgumentException("Invalid language id"));
			langProf.add(new Proficiency<Language>(l, ProficiencySource.race));
		}
		builder.languages(langProf);

		Map<Ability, AbilityInput> abilities = new HashMap<>();
		for (AbilityInput ai : in.getAbilityScores()) abilities.put(ai.getLabel(), ai);
		builder.abilities(abilities);

		if (isCreate) {
			builder.level(1)
			.skills(dndClass.getSkillProficiencies().getDefaults())
			.armor(null)
			.weapons(new ArrayList<>())
			.spells(new ArrayList<>());
			return mapper.toComplete(repo.save(builder.build()));
		} else {
			DndCharacter c = repo.findById(in.getId()).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
			builder.id(in.getId())
			.level(c.getLevel())
			// reset skill proficiencies to default if class has changed to avoid having too many
			.skills(c.getDndClass() == dndClass ? c.getSkills() : dndClass.getSkillProficiencies().getDefaults())
			.armor(c.getArmor())
			.weapons(c.getWeapons())
			// reset spell list if class has changed to avoid having incompatible spells
			.spells(c.getDndClass() == dndClass ? c.getSpells() : new ArrayList<>());
			return mapper.toComplete(repo.save(builder.build()));
		}
	}

	public void deleteCharacter(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (repo.existsById(id) == false) throw new NoSuchElementException("Entity with given ID not found");
		repo.deleteById(id);
	}

	public List<SkillOutput> editSkills(String id, List<SkillInput> skills) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		HashSet<Skill> newSkills = new HashSet<>();
		for (SkillInput si : skills) if (si.getProficient()) newSkills.add(si.getName());
		if (newSkills.size() > c.getDndClass().getSkillProficiencies().getAmount()) throw new IllegalArgumentException("Too many skill proficiencies");
		return mapper.toSkills(repo.save(c.toBuilder().skills(newSkills).build()));
	}

	public Armor editArmor(String id, String armorId) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		Armor a = armorId.isEmpty() ? null :
			armorRepo.findById(armorId).orElseThrow(() -> new NoSuchElementException("Armor with given ID not found"));
		return repo.save(c.toBuilder().armor(a).build()).getArmor();
	}

	public List<AttackOutput> editWeapons(String id, List<String> weaponIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		List<Weapon> newWeapons = new ArrayList<>();
		for (String wid : weaponIds) {
			if (wid == null) throw new IllegalArgumentException("Weapon ID cannot be null");
			Weapon w = weaponRepo.findById(wid).orElseThrow(() -> new NoSuchElementException("Weapon with given ID not found"));
			newWeapons.add(w);
		}
		return mapper.toAttacks(repo.save(c.toBuilder().weapons(newWeapons).build()));
	}

	public List<Spell> editSpells(String id, List<String> spellIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		if (c.getDndClass().getSpellcasting() == null) throw new IllegalArgumentException("Character's class cannot cast spells");
		List<Spell> newSpells = new ArrayList<>();
		for (String sid : spellIds) {
			if (sid == null) throw new IllegalArgumentException("Spell ID cannot be null");
			Spell sp = spellRepo.findById(sid).orElseThrow(() -> new NoSuchElementException("Spell with given ID not found"));
			newSpells.add(sp);
		}
		return repo.save(c.toBuilder().spells(newSpells).build()).getSpells();
	}

	public CharacterComplete levelUp(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		if (c.getLevel() == 20) throw new IllegalArgumentException("Character is already at max level");
		return mapper.toComplete(repo.save(c.toBuilder().level(c.getLevel() + 1).build()));
	}
}
