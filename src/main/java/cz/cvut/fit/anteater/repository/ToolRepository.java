package cz.cvut.fit.anteater.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.cvut.fit.anteater.entity.Tool;
import cz.cvut.fit.anteater.enumeration.ToolType;

public interface ToolRepository extends JpaRepository<Tool, Long> {
	public Tool findByName(String name);
	public List<Tool> findByType(ToolType type);
}
