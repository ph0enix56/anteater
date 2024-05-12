package cz.cvut.fit.anteater.business.sourcable;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.business.abstracts.SourcableBaseService;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.sourcable.ToolRepository;

@Service
public class ToolService extends SourcableBaseService<Tool>{
	public ToolService(ToolRepository repo) {
		super(repo);
	}
}
