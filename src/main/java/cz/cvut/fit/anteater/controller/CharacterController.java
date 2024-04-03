package cz.cvut.fit.anteater.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cz.cvut.fit.anteater.business.CharacterService;
import cz.cvut.fit.anteater.dto.request.CharacterInput;
import cz.cvut.fit.anteater.dto.request.IdWrapper;
import cz.cvut.fit.anteater.dto.request.SkillInput;
import cz.cvut.fit.anteater.dto.response.AttackOutput;
import cz.cvut.fit.anteater.dto.response.CharacterComplete;
import cz.cvut.fit.anteater.dto.response.CharacterShort;
import cz.cvut.fit.anteater.dto.response.SkillOutput;
import cz.cvut.fit.anteater.model.constants.Constants;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.model.entity.Spell;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = Constants.FRONTEND_URL)
@RestController
@RequestMapping(Constants.BASE_API_URL + "characters")
public class CharacterController {
	private CharacterService characterService;

	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	@GetMapping
	public List<CharacterShort> getAll() {
		return characterService.getAllCharacters();
	}

	@GetMapping("/{id}")
	public CharacterComplete getCompleteById(@PathVariable String id) {
		return characterService.getCompleteCharacter(id);
	}

	@PostMapping
	public CharacterComplete createCharacter(@RequestBody @Valid CharacterInput entity) {
		try {
			if (entity.getId() != null) throw new IllegalArgumentException("ID must be null");
			return characterService.saveCharacter(entity, true);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public CharacterComplete updateCharacter(@PathVariable String id, @RequestBody @Valid CharacterInput entity) {
		try {
			if (entity.getId() == null) throw new IllegalArgumentException("ID cannot be null");
			if (!Objects.equals(id, entity.getId())) throw new IllegalArgumentException("ID in path and body must match");
			return characterService.saveCharacter(entity, false);
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
	public List<SkillOutput> editSkills(@PathVariable String id, @RequestBody @Valid List<SkillInput> skills) {
		return characterService.editSkills(id, skills);
	}

	@PutMapping("/{id}/weapons")
	public List<AttackOutput> editWeapons(@PathVariable String id, @RequestBody List<String> weaponIds) {
		return characterService.editWeapons(id, weaponIds);
	}

	@PutMapping("/{id}/armor")
	public Armor editArmor(@PathVariable String id, @RequestBody @Valid IdWrapper armorId) {
		return characterService.editArmor(id, armorId.getId());
	}

	@PutMapping("/{id}/spells")
	public List<Spell> editSpells(@PathVariable String id, @RequestBody List<String> spellIds) {
		return characterService.editSpells(id, spellIds);
	}

	@PostMapping("/{id}/levelup")
	public CharacterComplete levelUp(@PathVariable String id) {
		return characterService.levelUp(id);
	}

	@GetMapping("/{id}/pdf")
	public ResponseEntity<Resource> getPdf(@PathVariable String id) {
		Resource pdf = characterService.getPdf(id);
		return ResponseEntity.ok()
			.header("Content-Disposition", "attachment; filename=\"" + pdf.getFilename() + "\"")
			.body(pdf);
	}
}
