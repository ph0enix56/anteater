package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class LanguageService extends BaseService<Language> {
	public LanguageService(LanguageRepository repository, SourceRepository sourceRepository) {
		super(repository, sourceRepository);
	}
}
