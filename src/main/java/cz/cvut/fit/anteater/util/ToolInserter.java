package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.enumeration.ToolType;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.ToolRepository;

public class ToolInserter {
	private ToolRepository toolRepository;

	public ToolInserter(ToolRepository toolRepository) {
		this.toolRepository = toolRepository;
	}

	//public void insert() {
	//	toolRepository.save(new Tool("Alchemist's supplies", ToolType.artisan));
	//	toolRepository.save(new Tool("Brewer's supplies", ToolType.artisan));
	//	toolRepository.save(new Tool("Calligrapher's supplies", ToolType.artisan));
	//	toolRepository.save(new Tool("Carpenter's tools", ToolType.artisan));
	//	toolRepository.save(new Tool("Cartographer's tools", ToolType.artisan));
	//	toolRepository.save(new Tool("Cobbler's tools", ToolType.artisan));
	//	toolRepository.save(new Tool("Cook's utensils", ToolType.artisan));
	//	// ...
	//	toolRepository.save(new Tool("Bagpipes", ToolType.instrument));
	//	toolRepository.save(new Tool("Drum", ToolType.instrument));
	//	toolRepository.save(new Tool("Dulcimer", ToolType.instrument));
	//	toolRepository.save(new Tool("Flute", ToolType.instrument));
	//	// ...
	//	toolRepository.save(new Tool("Dice set", ToolType.gaming));
	//	toolRepository.save(new Tool("Playing card set", ToolType.gaming));
	//	// ...
	//	toolRepository.save(new Tool("Vehicles (land)", ToolType.vehicle));
	//	toolRepository.save(new Tool("Vehicles (water)", ToolType.vehicle));
	//	// ...
	//	toolRepository.save(new Tool("Disguise kit", ToolType.other));
	//	toolRepository.save(new Tool("Forgery kit", ToolType.other));
	//	toolRepository.save(new Tool("Herbalism kit", ToolType.other));
	//	toolRepository.save(new Tool("Navigator's tools", ToolType.other));
	//	toolRepository.save(new Tool("Poisoner's kit", ToolType.other));
	//	toolRepository.save(new Tool("Thieves' tools", ToolType.other));
	//}
}
