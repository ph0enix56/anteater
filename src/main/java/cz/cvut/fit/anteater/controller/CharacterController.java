package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.dto.CharacterStats;
import cz.cvut.fit.anteater.service.CharacterService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/characters")
public class CharacterController {
	private CharacterService characterService;

	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	//@GetMapping("/{id}/info")
	//public SomeData getMethodName(@PathVariable String id) {

	//}

	@GetMapping("/{id}/stats")
	public CharacterStats getCharacterStats(@PathVariable String id) {
		return characterService.getCharacterStats(id);
	}

	//@GetMapping("/{id}/abilities")
	//public SomeData getMethodName(@PathVariable String id) {

	//}

	//@GetMapping("/{id}/skills")
	//public SomeData getMethodName(@RequestParam String param) {
	//	return new SomeData();
	//}
	
	//@GetMapping("/{id}/skills/{skill}")
	//public SomeData getMethodName(@PathVariable String id, @PathVariable String skill) {

	//}
}
