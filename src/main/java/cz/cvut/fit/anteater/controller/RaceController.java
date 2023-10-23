package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.entity.Race;
import cz.cvut.fit.anteater.enumeration.Ability;
import cz.cvut.fit.anteater.service.RaceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class RaceController {
	private RaceService raceService;

	public RaceController(RaceService raceService) {
		this.raceService = raceService;
	}

	@GetMapping("races")
	public Iterable<Race> getRaces() {
		return raceService.findAll();
	}

	@GetMapping("races/{id}")
	public Race getRace(@PathVariable String id) {
		Race subject = raceService.findById(id);
		System.out.println(subject.getAbilityScoresPlus2());
		System.out.println(subject.getAbilityScoresPlus2().getDefaults().contains(Ability.charisma));
		System.out.println(subject.getAbilityScoresPlus2().getDefaults().contains(Ability.strength));
		return subject;
	}
}
