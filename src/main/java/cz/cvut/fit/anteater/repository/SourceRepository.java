package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Source;

public interface SourceRepository extends MongoRepository<Source, String> {}
