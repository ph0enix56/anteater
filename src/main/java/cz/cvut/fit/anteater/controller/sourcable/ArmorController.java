package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.ArmorService;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.Armor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/armor")
public class ArmorController extends SourcableBaseController<Armor> {
	public ArmorController(ArmorService armorService) {
		super(armorService);
	}
}
