package cz.cvut.fit.anteater.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fit.anteater.entity.Background;

public interface BackgroundRepository extends JpaRepository<Background, Long> {
}
