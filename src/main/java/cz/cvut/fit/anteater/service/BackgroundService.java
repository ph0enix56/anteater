package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Background;
import cz.cvut.fit.anteater.repository.BackgroundRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class BackgroundService extends BaseService<Background> {
	public BackgroundService(BackgroundRepository repository, SourceRepository sourceRepository) {
		super(repository, sourceRepository);
	}
}
