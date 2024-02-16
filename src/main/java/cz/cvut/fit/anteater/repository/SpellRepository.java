package cz.cvut.fit.anteater.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.Spell;

public interface SpellRepository extends BaseRepository<Spell> {

	@Query("{ level: { $lte: ?0 }, dndClassIds: ?1, source: { $in: ?2 } }")
	List<Spell> findAvailable(int level, String dndClassId, List<Source> sources);
}
