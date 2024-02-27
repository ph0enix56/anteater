package cz.cvut.fit.anteater.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.enumeration.Skill;
import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.SkillInput;
import cz.cvut.fit.anteater.model.dto.SkillOutput;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.model.mapping.CharacterMapper;
import cz.cvut.fit.anteater.repository.ArmorRepository;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.SpellRepository;
import cz.cvut.fit.anteater.repository.WeaponRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repo;
	private DndClassRepository classRepo;
	private RaceRepository raceRepo;
	private BackgroundRepository backgroundRepo;
	private WeaponRepository weaponRepo;
	private SpellRepository spellRepo;
	private ArmorRepository armorRepo;
	private CharacterMapper mapper;

	public CharacterService(DndCharacterRepository repository, DndClassRepository classRepository,
			RaceRepository raceRepository, BackgroundRepository backgroundRepository,
			WeaponRepository weaponRepository, SpellRepository spellRepository,
			ArmorRepository armorRepository, CharacterMapper mapper) {
		this.repo = repository;
		this.classRepo = classRepository;
		this.raceRepo = raceRepository;
		this.backgroundRepo = backgroundRepository;
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

	// TODO: this will not work now
	public DndCharacter saveCharacter(CharacterInput in, Boolean isUpdate) {
		if (in == null) throw new IllegalArgumentException("Entity cannot be null");
		var builder = DndCharacter.builder()
			.characterName(in.getCharacterName())
			.playerName(in.getPlayerName())
			.cardPhotoUrl(in.getCardPhotoUrl())
			.sheetPhotoUrl(in.getSheetPhotoUrl())
			.level(in.getLevel())
			.skills(in.getSkillProficiencies())
			.saves(in.getSaveProficiencies())
			.dndClass(classRepo.findById(in.getDndClass()).orElseThrow(() -> new IllegalArgumentException("Invalid class id")))
			.race(raceRepo.findById(in.getRace()).orElseThrow(() -> new IllegalArgumentException("Invalid race id")))
			.background(backgroundRepo.findById(in.getBackground()).orElseThrow(() -> new IllegalArgumentException("Invalid background id")))
			.subclass(in.getSubclass());

		DndCharacter c = builder.build();
		if (isUpdate) {
			if (repo.existsById(in.getId()) == false) throw new IllegalArgumentException("Invalid update: entity does not exist"); 
			c.setId(in.getId());
		}
		return repo.save(c);
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

	public List<Weapon> editWeapons(String id, List<String> weaponIds) {
		if (id == null) throw new IllegalArgumentException("ID cannot be null");
		DndCharacter c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Character with given ID not found"));
		List<Weapon> newWeapons = new ArrayList<>();
		for (String wid : weaponIds) {
			if (wid == null) throw new IllegalArgumentException("Weapon ID cannot be null");
			Weapon w = weaponRepo.findById(wid).orElseThrow(() -> new NoSuchElementException("Weapon with given ID not found"));
			newWeapons.add(w);
		}
		c.setWeapons(newWeapons);
		return repo.save(c).getWeapons();
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
