package cz.cvut.fit.anteater.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;

public interface BaseRepository<T extends SourceableEntity> extends MongoRepository<T, String> {

	public List<T> findByName(String name);

	public List<T> findBySourceIn(Collection<Source> sources);

	public List<T> findByNameAndSourceIn(String name, Collection<Source> sources);
}
