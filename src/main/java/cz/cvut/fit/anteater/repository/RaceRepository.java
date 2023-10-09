package cz.cvut.fit.anteater.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fit.anteater.entity.Race;

public interface RaceRepository extends JpaRepository<Race, Long> {
	public Race findByName(String name);
}
