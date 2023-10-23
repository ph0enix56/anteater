package cz.cvut.fit.anteater.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.cvut.fit.anteater.entity.DndClass;

public interface DndClassRepository extends MongoRepository<DndClass, String>{}
