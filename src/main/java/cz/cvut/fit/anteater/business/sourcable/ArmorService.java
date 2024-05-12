package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.repository.sourcable.ArmorRepository;

@Service
public class ArmorService extends SourcableBaseService<Armor> {
	public ArmorService(ArmorRepository repo) {
		super(repo);
	}
}
