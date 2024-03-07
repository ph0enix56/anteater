package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.sourcable.LanguageRepository;

@Service
public class LanguageService extends SourcableBaseService<Language> {
	public LanguageService(LanguageRepository repo) {
		super(repo);
	}
}