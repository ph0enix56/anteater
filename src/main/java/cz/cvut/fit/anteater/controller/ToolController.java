package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.service.ToolService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/tools")
public class ToolController extends BaseController<Tool> {
	public ToolController(ToolService toolService) {
		super(toolService);
	}
}
