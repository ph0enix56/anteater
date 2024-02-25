package cz.cvut.fit.anteater.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cz.cvut.fit.anteater.model.dto.CharacterComplete;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.IdWrapper;
import cz.cvut.fit.anteater.model.dto.SkillInput;
import cz.cvut.fit.anteater.model.dto.SkillOutput;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.service.CharacterService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/characters")
public class CharacterController {
	private CharacterService characterService;

	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	@GetMapping
	public List<CharacterComplete> getCompleteAll() {
		return characterService.getAllCharacters();
	}

	@GetMapping("/{id}")
	public CharacterComplete getCompleteById(@PathVariable String id) {
		return characterService.getCompleteCharacter(id);
	}

	@PostMapping()
	public DndCharacter createCharacter(@RequestBody @Valid CharacterInput entity) {
		try {
			if (entity.getId() != null) throw new IllegalArgumentException("ID must be null");
			return characterService.saveCharacter(entity, false);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public DndCharacter updateCharacter(@PathVariable String id, @RequestBody @Valid CharacterInput entity) {
		try {
			if (entity.getId() == null) throw new IllegalArgumentException("ID cannot be null");
			if (!Objects.equals(id, entity.getId())) throw new IllegalArgumentException("ID in path and body must match");
			return characterService.saveCharacter(entity, true);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public String deleteCharacter(@PathVariable String id) {
		try {
			characterService.deleteCharacter(id);
			return "Character deleted";
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PutMapping("/{id}/skills")
	public List<SkillOutput> putMethodName(@PathVariable String id, @RequestBody List<SkillInput> skills) {
		return characterService.editSkills(id, skills);
	}

	@PutMapping("/{id}/armor")
	public Armor editArmor(@PathVariable String id, @RequestBody IdWrapper armorId) {
		return characterService.editArmor(id, armorId.getId());
	}

	@PutMapping("/{id}/spells")
	public List<Spell> editSpells(@PathVariable String id, @RequestBody List<String> spellIds) {
		return characterService.editSpells(id, spellIds);
	}
}
