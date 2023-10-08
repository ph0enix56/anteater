package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Language;

public interface LanguageRepository extends MongoRepository<Language, String> {
	public Language findByName(String name);
}
