package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.WeaponService;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.Weapon;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/weapons")
public class WeaponController extends SourcableBaseController<Weapon> {
	public WeaponController(WeaponService weaponService) {
		super(weaponService);
	}
}
