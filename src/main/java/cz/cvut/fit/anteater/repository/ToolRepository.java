package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.Tool;

public interface ToolRepository extends MongoRepository<Tool, String> {}
