package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.entity.Background;
import cz.cvut.fit.anteater.service.BackgroundService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class BackgroundController {
	private BackgroundService backgroundService;

	public BackgroundController(BackgroundService backgroundService) {
		this.backgroundService = backgroundService;
	}

	@GetMapping("backgrounds")
	public Iterable<Background> getBackgrounds() {
		return backgroundService.findAll();
	}

	@GetMapping("backgrounds/{id}")
	public Background getBackground(@PathVariable String id) {
		return backgroundService.findById(id);
	}
}
