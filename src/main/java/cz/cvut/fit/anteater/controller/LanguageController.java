package cz.cvut.fit.anteater.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.model.entity.Language;
import cz.cvut.fit.anteater.service.LanguageService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/languages")
public class LanguageController extends BaseController<Language> {
	public LanguageController(LanguageService languageService) {
		super(languageService);
	}
}
