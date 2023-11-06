package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.entity.Source;
import cz.cvut.fit.anteater.repository.SourceRepository;

public class SourceInserter {
	private SourceRepository sourceRepository;

	public SourceInserter(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}

	public void insert() {
		sourceRepository.save(new Source("srd", "System Reference Document"));
		sourceRepository.save(new Source("phb", "Player's Handbook"));
		sourceRepository.save(new Source("xge", "Xanathar's Guide to Everything"));
		sourceRepository.save(new Source("camp_lotr", "Lord of the Rings Campaign Items"));
		sourceRepository.save(new Source("misc", "Miscellaneous"));
	}
}
