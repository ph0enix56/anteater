package cz.cvut.fit.anteater.repository.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import lombok.NonNull;

public abstract class SourcableBaseRepository<T extends SourceableEntity> {

	private final MongoTemplate mongoTemplate;
	@NonNull private final Class<T> entityClass;

	public SourcableBaseRepository(MongoTemplate mongoTemplate, Class<T> entityClass) {
		this.mongoTemplate = mongoTemplate;
		this.entityClass = entityClass;
	}

	public long count() {
		return mongoTemplate.count(new Query(), entityClass);
	}

	public List<T> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	public Optional<T> findById(@NonNull String id) {
		return Optional.ofNullable(mongoTemplate.findById(id, entityClass));
	}

	public Optional<T> findWithinSources(@NonNull String id, @NonNull List<String> sourceIds) {
		Query query = new Query(Criteria.where("id").is(id));
		if (!sourceIds.isEmpty()) query.addCriteria(Criteria.where("source.id").in(sourceIds));
		return Optional.ofNullable(mongoTemplate.findOne(query, entityClass));
	}

	public boolean existsById(@NonNull String id) {
		return mongoTemplate.exists(new Query(Criteria.where("id").is(id)), entityClass);
	}

	public Page<T> search(String name, List<String> sourceIds, @NonNull Pageable pageable) {
		Query query = new Query();
		if (name != null) query.addCriteria(Criteria.where("name").regex(name, "i"));
		if (sourceIds != null && !sourceIds.isEmpty()) {
			query.addCriteria(Criteria.where("source.id").in(sourceIds));
		}
		long total = mongoTemplate.count(query, entityClass);
		query.with(pageable);

		List<T> content = mongoTemplate.find(query, entityClass);
		return new PageImpl<>(content, pageable, total);
	}

	public T save(@NonNull T entity) {
		return mongoTemplate.save(entity);
	}

	public void deleteById(@NonNull String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), entityClass);
	}
}
