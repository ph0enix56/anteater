package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.LanguageRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LanguageInserter {
	private final LanguageRepository languageRepository;

	public void insert() {
		languageRepository.save(Language.builder().name("Common").exotic(false).build());
		languageRepository.save(Language.builder().name("Dwarvish").exotic(false).build());
		languageRepository.save(Language.builder().name("Elvish").exotic(false).build());
		languageRepository.save(Language.builder().name("Giant").exotic(false).build());
		languageRepository.save(Language.builder().name("Gnomish").exotic(false).build());
		languageRepository.save(Language.builder().name("Goblin").exotic(false).build());
		languageRepository.save(Language.builder().name("Halfling").exotic(false).build());
		languageRepository.save(Language.builder().name("Orc").exotic(false).build());
		languageRepository.save(Language.builder().name("Abyssal").exotic(true).build());
		languageRepository.save(Language.builder().name("Celestial").exotic(true).build());
		languageRepository.save(Language.builder().name("Draconic").exotic(true).build());
		languageRepository.save(Language.builder().name("Deep Speech").exotic(true).build());
		languageRepository.save(Language.builder().name("Infernal").exotic(true).build());
		languageRepository.save(Language.builder().name("Primordial").exotic(true).build());
		languageRepository.save(Language.builder().name("Sylvan").exotic(true).build());
		languageRepository.save(Language.builder().name("Undercommon").exotic(true).build());
	}
}
