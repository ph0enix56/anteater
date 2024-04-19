package cz.cvut.fit.anteater.controller.sourcable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.anteater.business.sourcable.DndClassService;
import cz.cvut.fit.anteater.constants.Constants;
import cz.cvut.fit.anteater.controller.abstracts.SourcableBaseController;
import cz.cvut.fit.anteater.model.entity.DndClass;

@CrossOrigin(origins = Constants.FRONTEND_URL)
@RestController
@RequestMapping(Constants.BASE_API_URL + "classes")
public class DndClassController extends SourcableBaseController<DndClass> {
	public DndClassController(DndClassService dndClassService) {
		super(dndClassService);
	}
}
