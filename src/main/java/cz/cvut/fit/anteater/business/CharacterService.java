package cz.cvut.fit.anteater.business;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.mapping.CharacterMapper;
import cz.cvut.fit.anteater.constants.Constants;
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
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.model.entity.Character;
import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.value.Proficiency;
import cz.cvut.fit.anteater.repository.CharacterRepository;
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
	private CharacterRepository repo;
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

	public CharacterService(CharacterRepository repository, SourceRepository sourceRepository,
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
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		return mapper.toComplete(c);
	}

	public Resource getPdf(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		String outputPath = Constants.PDF_EXPORT_DIRECTORY + c.getId() + ".pdf";
		pdfExporter.exportToPDF(mapper.toPdfOutput(c), outputPath, Constants.PDF_TEMPLATE_FILE, Constants.PDF_ATTACK_COUNT);
		try {
			return new UrlResource(Paths.get(outputPath).toUri());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Failed to load PDF file");
		}
	}

	private void validateProficiencyAmount(List<String> ids, int max, String message) {
		if (ids.size() > max) throw new IllegalArgumentException(message);
	}

	private <T extends SourceableEntity> List<Proficiency<T>> buildProficiencies(List<String> ids,
			Function<String, T> finder, ProficiencySource source) {
		List<Proficiency<T>> proficiencies = new ArrayList<>();
		for (String id : ids) proficiencies.add(new Proficiency<>(finder.apply(id), source));
		return proficiencies;
	}

	// if the character has no armor or the armor is not available, return no armor
	// otherwise keep the current armor
	private Armor handleArmorEdit(Character c, List<String> sourceIds) {
		if (c.getArmor().getSource() == null) return null;
		boolean isAvailable = sourceIds.isEmpty() || sourceIds.contains(c.getArmor().getSource().getId());
		return isAvailable ? c.getArmor() : null;
	}

	public CharacterComplete saveCharacter(CharacterInput in, Boolean isCreate) {
		if (in == null) throw new IllegalArgumentException("Entity cannot be null");
		List<String> sourceIds = in.getInfo().getSourceIds();

		// validate class, race, background and if subclass and size are valid for the selected
		DndClass dndClass = classRepo.findWithinSources(in.getDndClass().getId(), sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Class not found within character sources"));
		Race race = raceRepo.findWithinSources(in.getRace().getId(), sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Race not found within character sources"));
		Background background = backgroundRepo.findWithinSources(in.getBackground().getId(), sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Background not found within character sources"));
		if (race.getSizeOptions().contains(in.getRace().getSize()) == false) throw new IllegalArgumentException("Invalid size selected");
		if (dndClass.getSubclasses().contains(in.getDndClass().getSubclass()) == false) throw new IllegalArgumentException("Invalid subclass selected");

		var builder = Character.builder()
			// set default values if none are provided
			.characterName(Optional.ofNullable(in.getInfo().getCharacterName()).orElse("Unnamed Character"))
			.playerName(Optional.ofNullable(in.getInfo().getPlayerName()).orElse("No Player Name"))
			.cardPhotoUrl(Optional.ofNullable(in.getInfo().getCardPhotoUrl()).orElse(""))
			.sheetPhotoUrl(Optional.ofNullable(in.getInfo().getSheetPhotoUrl()).orElse(""))
			.sources(sourceIds.stream().map(id -> sourceRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid source id"))).toList())
			.dndClass(dndClass)
			.subclass(in.getDndClass().getSubclass())
			.race(race)
			.size(in.getRace().getSize())
			.background(background);

		validateProficiencyAmount(in.getBackground().getToolIds(), background.getToolProficiencies().getAmount(), "Too many background tool proficiencies");
		validateProficiencyAmount(in.getBackground().getLanguageIds(), background.getLanguageProficiencies().getAmount(), "Too many background language proficiencies");
		validateProficiencyAmount(in.getDndClass().getToolIds(), dndClass.getToolProficiencies().getAmount(), "Too many class tool proficiencies");
		validateProficiencyAmount(in.getRace().getLanguageIds(), race.getLanguageProficiencies().getAmount(), "Too many race language proficiencies");

		List<Proficiency<Tool>> toolProf = buildProficiencies(in.getBackground().getToolIds(), id -> toolRepo.findWithinSources(id, sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Tool (from background) not found within character sources")), ProficiencySource.background);
		toolProf.addAll(buildProficiencies(in.getDndClass().getToolIds(), id -> toolRepo.findWithinSources(id, sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Tool (from class) not found within character sources")), ProficiencySource.dndClass));
		builder.tools(toolProf);
	
		List<Proficiency<Language>> langProf = buildProficiencies(in.getBackground().getLanguageIds(), id -> languageRepo.findWithinSources(id, sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Language (from background) not found within character sources")), ProficiencySource.background);
		langProf.addAll(buildProficiencies(in.getRace().getLanguageIds(), id -> languageRepo.findWithinSources(id, sourceIds)
			.orElseThrow(() -> new IllegalArgumentException("Language (from race) not found within character sources")), ProficiencySource.race));
		builder.languages(langProf);

		Map<Ability, AbilityInput> abilities = new HashMap<>();
		for (AbilityInput ai : in.getAbilityScores()) abilities.put(ai.getLabel(), ai);
		builder.abilities(abilities);

		if (isCreate) {
			builder.level(1)
			.skills(background.getSkillProficiencies().getDefaults())
			.armor(null)
			.weapons(new ArrayList<>())
			.spells(new ArrayList<>());
			return mapper.toComplete(repo.save(builder.build()));
		} else {
			Character c = repo.findById(in.getId()).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
			builder.id(in.getId())
			.level(c.getLevel())
			.skills(c.getSkills())
			// unequip armor if it's no longer available
			.armor(handleArmorEdit(c, sourceIds))
			// remove weapons that are no longer available
			.weapons(c.getWeapons().stream().filter(w -> sourceIds.isEmpty() || sourceIds.contains(w.getSource().getId())).toList())
			// reset spell list if class has changed, otherwise remove spells that are no longer available
			.spells(c.getDndClass() == dndClass ?
				c.getSpells().stream().filter(s -> sourceIds.contains(s.getSource().getId())).toList() :
				new ArrayList<>());
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
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		HashSet<Skill> newSkills = new HashSet<>();
		for (SkillInput si : skills) if (si.getProficient()) newSkills.add(si.getName());
		return mapper.toSkills(repo.save(c.toBuilder().skills(newSkills).build()));
	}

	public Armor editArmor(String id, String armorId) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		Armor a = armorId.isEmpty() ? null :
			armorRepo.findWithinSources(armorId, c.getSources().stream().map(Source::getId).toList())
				.orElseThrow(() -> new NoSuchElementException("Armor not found within character sources"));
		return repo.save(c.toBuilder().armor(a).build()).getArmor();
	}

	public List<AttackOutput> editWeapons(String id, List<String> weaponIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		List<Weapon> newWeapons = new ArrayList<>();
		List<String> sourceIds = c.getSources().stream().map(Source::getId).toList();
		for (String wid : weaponIds) {
			if (wid == null) throw new IllegalArgumentException("Weapon ID cannot be null");
			Weapon w = weaponRepo.findWithinSources(wid, sourceIds)
				.orElseThrow(() -> new NoSuchElementException("Weapon not found within character sources"));
			newWeapons.add(w);
		}
		return mapper.toAttacks(repo.save(c.toBuilder().weapons(newWeapons).build()));
	}

	public List<Spell> editSpells(String id, List<String> spellIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		if (c.getDndClass().getSpellcasting() == null) throw new IllegalArgumentException("Character's class cannot cast spells");
		List<Spell> newSpells = new ArrayList<>();
		List<String> sourceIds = c.getSources().stream().map(Source::getId).toList();
		for (String sid : spellIds) {
			if (sid == null) throw new IllegalArgumentException("Spell ID cannot be null");
			Spell sp = spellRepo.findWithinSources(sid, sourceIds)
				.orElseThrow(() -> new NoSuchElementException("Spell not found within character sources"));
			newSpells.add(sp);
		}
		return repo.save(c.toBuilder().spells(newSpells).build()).getSpells();
	}

	public CharacterComplete levelUp(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		Character c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		if (c.getLevel() == 20) throw new IllegalArgumentException("Character is already at max level");
		return mapper.toComplete(repo.save(c.toBuilder().level(c.getLevel() + 1).build()));
	}
}
