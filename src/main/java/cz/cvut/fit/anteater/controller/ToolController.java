package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.service.ToolService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class ToolController {
	private ToolService toolService;

	public ToolController(ToolService toolService) {
		this.toolService = toolService;
	}

	@GetMapping("tools")
	public Iterable<Tool> getTools() {
		return toolService.findAll();
	}

	@GetMapping("tools/{id}")
	public Tool getTool(@PathVariable String id) {
		return toolService.findById(id);
	}
}
