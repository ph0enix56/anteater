package cz.cvut.fit.anteater.service;

import org.springframework.stereotype.Service;

import cz.cvut.fit.anteater.model.entity.Armor;
import cz.cvut.fit.anteater.repository.ArmorRepository;
import cz.cvut.fit.anteater.repository.SourceRepository;

@Service
public class ArmorService extends BaseService<Armor> {
	public ArmorService(ArmorRepository repository, SourceRepository sourceRepository) {
		super(repository, sourceRepository);
	}
}
