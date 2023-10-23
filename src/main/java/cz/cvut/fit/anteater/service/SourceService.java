package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.entity.Source;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class SourceService extends BaseService<Source> {
	public SourceService(SourceRepository repository) {
		super(repository);
	}
}
