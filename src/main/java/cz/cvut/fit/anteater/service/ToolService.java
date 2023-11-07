package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.ToolRepository;

@Service
public class ToolService extends BaseService<Tool>{
	public ToolService(ToolRepository repository) {
		super(repository);
	}
}
