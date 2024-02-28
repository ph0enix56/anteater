package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.enumeration.ProficiencySource;
import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.AbilityInput;
import cz.cvut.fit.anteater.model.dto.AttackOutput;
import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.SkillInput;
import cz.cvut.fit.anteater.model.dto.SkillOutput;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.mapping.CharacterMapper;
import cz.cvut.fit.anteater.model.value.Proficiency;
import cz.cvut.fit.anteater.repository.ArmorRepository;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;
import cz.cvut.fit.anteater.repository.SpellRepository;
import cz.cvut.fit.anteater.repository.ToolRepository;
import cz.cvut.fit.anteater.repository.WeaponRepository;

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

	public CharacterService(DndCharacterRepository repository, SourceRepository sourceRepository,
			DndClassRepository classRepository,	RaceRepository raceRepository,
			BackgroundRepository backgroundRepository, ToolRepository toolRepository,
			LanguageRepository languageRepository, WeaponRepository weaponRepository,
			SpellRepository spellRepository, ArmorRepository armorRepository,
			CharacterMapper mapper) {
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
	}

	public List<CharacterComplete> getAllCharacters() {
		return repo.findAll().stream().map(mapper::toComplete).toList();
	}

	public CharacterComplete getCompleteCharacter(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
		return mapper.toComplete(c);
	}

	public CharacterComplete saveCharacter(CharacterInput in, Boolean isCreate) {
		if (in == null) throw new IllegalArgumentException("Entity cannot be null");
		var builder = DndCharacter.builder()
			.characterName(in.getBasicInfo().getCharacterName())
			.playerName(in.getBasicInfo().getPlayerName())
			.cardPhotoUrl(in.getBasicInfo().getCardPhotoUrl())
			.sheetPhotoUrl(in.getBasicInfo().getSheetPhotoUrl())
			.sources(in.getBasicInfo().getSourceIds().stream().map(id -> sourceRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid source id"))).toList())
			.dndClass(classRepo.findById(in.getDndClass().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid class id")))
			.subclass(in.getDndClass().getSubclass())
			.race(raceRepo.findById(in.getRace().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid race id")))
			.size(in.getRace().getSize())
			.background(backgroundRepo.findById(in.getBackground().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid background id")));

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
				Language l =languageRepo.findById(lid).orElseThrow(() -> new IllegalArgumentException("Invalid language id"));
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
				.skills(new HashSet<>())
				.armor(armorRepo.findByNameLike("Unarmored").get(0))
				.weapons(new ArrayList<>())
				.spells(new ArrayList<>());
				return mapper.toComplete(repo.save(builder.build()));
			} else {
				DndCharacter c = repo.findById(in.getId()).orElseThrow(() -> new NoSuchElementException("Entity with given ID not found"));
				builder.id(in.getId())
				.level(c.getLevel())
				.skills(c.getSkills())
				.armor(c.getArmor())
				.weapons(c.getWeapons())
				.spells(c.getSpells());
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
		c.setSkills(newSkills);
		c = repo.save(c);
		return mapper.toSkills(c);
	}

	public Armor editArmor(String id, String armorId) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		if (armorId == null) throw new IllegalArgumentException("Armor ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		Armor a = armorRepo.findById(armorId).orElseThrow(() -> new NoSuchElementException("Armor with given ID not found"));
		c.setArmor(a);
		return repo.save(c).getArmor();
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
		c.setWeapons(newWeapons);
		repo.save(c);
		return mapper.toAttacks(c);
	}

	public List<Spell> editSpells(String id, List<String> spellIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		List<Spell> newSpells = new ArrayList<>();
		for (String sid : spellIds) {
			if (sid == null) throw new IllegalArgumentException("Spell ID cannot be null");
			Spell sp = spellRepo.findById(sid).orElseThrow(() -> new NoSuchElementException("Spell with given ID not found"));
			newSpells.add(sp);
		}
		c.setSpells(newSpells);
		return repo.save(c).getSpells();
	}

	public CharacterComplete levelUp(String id) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		if (c.getLevel() == 20) throw new IllegalArgumentException("Character is already at max level");
		c.setLevel(c.getLevel() + 1);
		return mapper.toComplete(repo.save(c));
	}
}
