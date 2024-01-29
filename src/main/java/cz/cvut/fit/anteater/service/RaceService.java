package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Race;
import cz.cvut.fit.anteater.repository.RaceRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class RaceService extends BaseService<Race> {
	public RaceService(RaceRepository repository, SourceRepository sourceRepository) {
		super(repository, sourceRepository);
	}
}
