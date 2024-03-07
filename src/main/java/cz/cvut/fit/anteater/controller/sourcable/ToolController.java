package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.ToolService;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.Tool;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/tools")
public class ToolController extends SourcableBaseController<Tool> {
	public ToolController(ToolService toolService) {
		super(toolService);
	}
}
