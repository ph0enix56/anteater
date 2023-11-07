package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.repository.LanguageRepository;

public class LanguageInserter {
	private LanguageRepository languageRepository;

	public LanguageInserter(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	//public void insert() {
	//	languageRepository.save(new Language("Common", false));
	//	languageRepository.save(new Language("Dwarvish", false));
	//	languageRepository.save(new Language("Elvish", false));
	//	languageRepository.save(new Language("Giant", false));
	//	languageRepository.save(new Language("Gnomish", false));
	//	languageRepository.save(new Language("Goblin", false));
	//	languageRepository.save(new Language("Halfling", false));
	//	languageRepository.save(new Language("Orc", false));
	//	languageRepository.save(new Language("Abyssal", true));
	//	languageRepository.save(new Language("Celestial", true));
	//	languageRepository.save(new Language("Draconic", true));
	//	languageRepository.save(new Language("Deep Speech", true));
	//	languageRepository.save(new Language("Infernal", true));
	//	languageRepository.save(new Language("Primordial", true));
	//	languageRepository.save(new Language("Sylvan", true));
	//	languageRepository.save(new Language("Undercommon", true));
	//}
}
