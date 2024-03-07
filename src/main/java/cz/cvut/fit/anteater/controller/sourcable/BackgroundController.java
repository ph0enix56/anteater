package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.BackgroundService;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.Background;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/backgrounds")
public class BackgroundController extends SourcableBaseController<Background> {
	public BackgroundController(BackgroundService backgroundService) {
		super(backgroundService);
	}
}
