package cz.cvut.fit.anteater.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.Source;
import cz.cvut.fit.anteater.model.entity.SourceableEntity;

public interface BaseRepository<T extends SourceableEntity> extends MongoRepository<T, String> {

	public List<T> findByNameLike(String name);

	public List<T> findBySourceIn(Collection<Source> sources);

	public List<T> findByNameLikeAndSourceIn(String name, Collection<Source> sources);

	public Page<T> findAll(Pageable pageable);

	public Page<T> findByNameLike(String name, Pageable pageable);

	public Page<T> findBySourceIn(Collection<Source> sources, Pageable pageable);

	public Page<T> findByNameLikeAndSourceIn(String name, Collection<Source> sources, Pageable pageable);
}
