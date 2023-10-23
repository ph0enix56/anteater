package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.entity.Language;
import cz.cvut.fit.anteater.service.BaseService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class LanguageController {
	private BaseService<Language> languageService;

	public LanguageController(BaseService<Language> languageService) {
		this.languageService = languageService;
	}

	@GetMapping("languages")
	public Iterable<Language> getLanguages() {
	return languageService.findAll();
	}

	@GetMapping("languages/{id}")
	public Language getLanguage(@PathVariable String id) {
	return languageService.findById(id);
	}
}
