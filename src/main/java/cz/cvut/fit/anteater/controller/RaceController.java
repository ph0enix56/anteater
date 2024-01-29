package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.service.RaceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/races")
public class RaceController extends BaseController<Race> {
	public RaceController(RaceService raceService) {
		super(raceService);
	}
}
