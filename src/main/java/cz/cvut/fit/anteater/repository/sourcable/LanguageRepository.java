package cz.cvut.fit.anteater.repository.sourcable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.abstracts.SourcableBaseRepository;

@Repository
public class LanguageRepository extends SourcableBaseRepository<Language> {
	public LanguageRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Language.class);
	}
}
