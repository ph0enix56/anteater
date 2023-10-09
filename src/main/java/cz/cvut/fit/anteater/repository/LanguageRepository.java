package cz.cvut.fit.anteater.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fit.anteater.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
	public Language findByName(String name);
}
