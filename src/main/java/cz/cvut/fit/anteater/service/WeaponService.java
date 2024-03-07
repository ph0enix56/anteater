package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Weapon;
import cz.cvut.fit.anteater.repository.SourceRepository;
import cz.cvut.fit.anteater.repository.WeaponRepository;

@Service
public class WeaponService extends SourcableService<Weapon> {
	public WeaponService(WeaponRepository weaponRepository, SourceRepository sourceRepository) {
		super(weaponRepository, sourceRepository);
	}
}
