package cz.cvut.fit.anteater.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.service.SpellService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/spells")
public class SpellController extends BaseController<Spell> {
	private SpellService spellService;

	public SpellController(SpellService spellService) {
		super(spellService);
		this.spellService = spellService;
	}

	@GetMapping("/available/{characterId}")
	public List<Spell> findAvailableForCharacter(@PathVariable String characterId) {
		return spellService.findAvailableForCharacter(characterId);
	}
}
