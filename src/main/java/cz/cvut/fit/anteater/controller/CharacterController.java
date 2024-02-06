package cz.cvut.fit.anteater.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.service.CharacterService;

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
}
