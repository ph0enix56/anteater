package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.entity.Background;
import cz.cvut.fit.anteater.repository.BackgroundRepository;

@Service
public class BackgroundService extends BaseService<Background> {
	public BackgroundService(BackgroundRepository repository) {
		super(repository);
	}
}
