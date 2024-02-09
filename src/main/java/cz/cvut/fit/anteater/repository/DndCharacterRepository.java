package cz.cvut.fit.anteater.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.dto.CharacterInfo;
import cz.cvut.fit.anteater.model.entity.DndCharacter;

public interface DndCharacterRepository extends MongoRepository<DndCharacter, String> {
	List<CharacterInfo> findAllCharacterInfosBy();
	Optional<CharacterInfo> findCharacterInfoById(String id);
}
