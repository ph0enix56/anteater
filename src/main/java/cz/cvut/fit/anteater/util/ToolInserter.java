package cz.cvut.fit.anteater.util;

import cz.cvut.fit.anteater.enumeration.ToolType;
import cz.cvut.fit.anteater.model.entity.Tool;
import cz.cvut.fit.anteater.repository.ToolRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ToolInserter {
	private final ToolRepository toolRepository;

	public void insert() {
		toolRepository.save(Tool.builder().name("Alchemist's supplies").type(ToolType.artisan).build());
		toolRepository.save(Tool.builder().name("Brewer's supplies").type(ToolType.artisan).build());
		toolRepository.save(Tool.builder().name("Calligrapher's supplies").type(ToolType.artisan).build());
		toolRepository.save(Tool.builder().name("Carpenter's tools").type(ToolType.artisan).build());
		toolRepository.save(Tool.builder().name("Cartographer's tools").type(ToolType.artisan).build());
		// ...
		toolRepository.save(Tool.builder().name("Drum").type(ToolType.instrument).build());
		toolRepository.save(Tool.builder().name("Dulcimer").type(ToolType.instrument).build());
		toolRepository.save(Tool.builder().name("Flute").type(ToolType.instrument).build());
		toolRepository.save(Tool.builder().name("Lute").type(ToolType.instrument).build());
		// ...
		toolRepository.save(Tool.builder().name("Disguise kit").type(ToolType.other).build());
		toolRepository.save(Tool.builder().name("Forgery kit").type(ToolType.other).build());
		toolRepository.save(Tool.builder().name("Herbalism kit").type(ToolType.other).build());
		toolRepository.save(Tool.builder().name("Navigator's tools").type(ToolType.other).build());
		toolRepository.save(Tool.builder().name("Thieves' tools").type(ToolType.other).build());
		// ...
		toolRepository.save(Tool.builder().name("Dice set").type(ToolType.gaming).build());
		toolRepository.save(Tool.builder().name("Playing card set").type(ToolType.gaming).build());
		// ...
		toolRepository.save(Tool.builder().name("Vehicles (land)").type(ToolType.vehicle).build());
		toolRepository.save(Tool.builder().name("Vehicles (water)").type(ToolType.vehicle).build());
	}
}
