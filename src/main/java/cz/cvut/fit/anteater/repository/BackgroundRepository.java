package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Background;

public interface BackgroundRepository extends MongoRepository<Background, String> {}
