package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.repository.sourcable.WeaponRepository;

@Service
public class WeaponService extends SourcableBaseService<Weapon> {
	public WeaponService(WeaponRepository repo) {
		super(repo);
	}
}
