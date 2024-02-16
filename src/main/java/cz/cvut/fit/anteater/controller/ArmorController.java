package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.service.ArmorService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/armor")
public class ArmorController extends BaseController<Armor> {
	public ArmorController(ArmorService armorService) {
		super(armorService);
	}
}
