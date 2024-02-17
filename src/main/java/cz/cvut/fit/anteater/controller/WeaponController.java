package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.service.WeaponService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/armor")
public class WeaponController extends BaseController<Weapon> {
	public WeaponController(WeaponService weaponService) {
		super(weaponService);
	}
}
