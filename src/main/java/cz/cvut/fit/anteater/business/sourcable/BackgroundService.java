package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.repository.sourcable.BackgroundRepository;

@Service
public class BackgroundService extends SourcableBaseService<Background> {
	public BackgroundService(BackgroundRepository repo) {
		super(repo);
	}
}
