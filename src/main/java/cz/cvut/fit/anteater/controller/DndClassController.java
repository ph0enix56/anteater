package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.DndClass;
import cz.cvut.fit.anteater.service.DndClassService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/classes")
public class DndClassController extends BaseController<DndClass> {
	public DndClassController(DndClassService dndClassService) {
		super(dndClassService);
	}
}
