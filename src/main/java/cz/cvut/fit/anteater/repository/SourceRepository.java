package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.model.entity.Source;

// TODO: sources not yet implemented
public interface SourceRepository extends MongoRepository<Source, String> {}
