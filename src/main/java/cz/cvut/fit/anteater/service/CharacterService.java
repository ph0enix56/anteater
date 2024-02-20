package cz.cvut.fit.anteater.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.CharacterShort;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.mapping.CharacterMapper;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.DndClassRepository;
import cz.cvut.fit.anteater.repository.RaceRepository;

@Service
public class CharacterService {
	private DndCharacterRepository repo;
	private DndClassRepository classRepo;
	private RaceRepository raceRepo;
	private BackgroundRepository backgroundRepo;
	private CharacterMapper mapper;

	public CharacterService(DndCharacterRepository repository, DndClassRepository classRepository,
			RaceRepository raceRepository, BackgroundRepository backgroundRepository, CharacterMapper mapper) {
		this.repo = repository;
		this.classRepo = classRepository;
		this.raceRepo = raceRepository;
		this.backgroundRepo = backgroundRepository;
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
}
