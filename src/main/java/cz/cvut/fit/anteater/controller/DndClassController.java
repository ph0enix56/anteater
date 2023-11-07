package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.service.DndClassService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class DndClassController {
	private DndClassService dndClassService;

	public DndClassController(DndClassService dndClassService) {
		this.dndClassService = dndClassService;
	}

	@GetMapping("classes")
	public Iterable<DndClass> getDndClasses() {
		return dndClassService.findAll();
	}

	@GetMapping("classes/{id}")
	public DndClass getDndClass(@PathVariable String id) {
		return dndClassService.findById(id);
	}
}
