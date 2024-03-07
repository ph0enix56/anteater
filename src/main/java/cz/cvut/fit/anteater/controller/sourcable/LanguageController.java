package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.LanguageService;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.Language;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/languages")
public class LanguageController extends SourcableBaseController<Language> {
	public LanguageController(LanguageService languageService) {
		super(languageService);
	}
}
