package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.repository.sourcable.SpellRepository;

@Service
public class SpellService extends SourcableBaseService<Spell> {
	public SpellService(SpellRepository repo) {
		super(repo);
	}
}
