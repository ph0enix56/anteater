package cz.cvut.fit.anteater.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.DndCharacter;
import cz.cvut.fit.anteater.model.entity.Spell;
import cz.cvut.fit.anteater.repository.DndCharacterRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;
import cz.cvut.fit.anteater.repository.SpellRepository;

@Service
public class SpellService extends BaseService<Spell> {
	private SpellRepository repository;
	private DndCharacterRepository characterRepository;

	public SpellService(SpellRepository repository, SourceRepository sourceRepository, DndCharacterRepository characterRepository) {
		super(repository, sourceRepository);
		this.repository = repository;
		this.characterRepository = characterRepository;
	}

	public List<Spell> findAvailableForCharacter(String characterId) {
		DndCharacter ch = characterRepository.findById(characterId).orElseThrow(() -> new IllegalArgumentException("Character with given ID not found"));
		Integer maxSpellLevel = 0;
		if (ch.getDndClass().getSpellcasting() != null) {
			maxSpellLevel = ch.getDndClass().getSpellcasting().getMaxSlotLevelForLevel(ch.getLevel());
		}
		return repository.findAvailable(maxSpellLevel, ch.getDndClass().getId(), ch.getSources());
	}
}
