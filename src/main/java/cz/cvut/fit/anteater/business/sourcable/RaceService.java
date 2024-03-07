package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.repository.sourcable.RaceRepository;

@Service
public class RaceService extends SourcableBaseService<Race> {
	public RaceService(RaceRepository repo) {
		super(repo);
	}
}
