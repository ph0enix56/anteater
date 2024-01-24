package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Source;
//import cz.cvut.fit.anteater.service.SourceService;

//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("api/")
//public class SourceController {
//	private SourceService sourceService;

//	public SourceController(SourceService sourceService) {
//		this.sourceService = sourceService;
//	}

//	@GetMapping("sources")
//	public Iterable<Source> getSources() {
//		return sourceService.findAll();
//	}

//	@GetMapping("sources/{id}")
//	public Source getSource(@PathVariable String id) {
//		return sourceService.findById(id);
//	}
//}
