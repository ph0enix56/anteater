package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.service.BackgroundService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/backgrounds")
public class BackgroundController extends BaseController<Background> {
	public BackgroundController(BackgroundService backgroundService) {
		super(backgroundService);
	}
}
