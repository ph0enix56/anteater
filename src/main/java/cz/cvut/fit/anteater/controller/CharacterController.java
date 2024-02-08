package cz.cvut.fit.anteater.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterInput;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.service.CharacterService;
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
	public List<CharacterInfo> getCharacterInfos() {
		return characterService.getCharacterInfos();
	}

	@GetMapping("/{id}/info")
	public CharacterInfo getCharacterInfo(@PathVariable String id) {
		return characterService.getCharacterInfo(id);
	}

	@GetMapping("/{id}/stats")
	public CharacterStats getCharacterStats(@PathVariable String id) {
		return characterService.getCharacterStats(id);
	}

	@PostMapping()
	public DndCharacter createCharacter(@RequestBody CharacterInput entity) {
		try {
			if (entity.getId() != null) throw new IllegalArgumentException("ID must be null");
			System.out.println("Got input:" + entity);
			return characterService.saveCharacter(entity, false);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public DndCharacter updateCharacter(@PathVariable String id, @RequestBody CharacterInput entity) {
		try {
			if (id != entity.getId()) throw new IllegalArgumentException("ID in path and body do not match");
			return characterService.saveCharacter(entity, true);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
