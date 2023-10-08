//package cz.cvut.fit.anteater.service;

//import java.util.List;

//import org.springframework.stereotype.Service;

//import cz.cvut.fit.anteater.repository.BaseNameRepository;

//@Service
//public abstract class BaseService<T> {
//	private BaseNameRepository<T> repository;

//	public BaseService(BaseNameRepository<T> repository) {
//		this.repository = repository;
//	}

//	public List<T> findAll() {
//		return repository.findAll();
//	}

//	public T findById(String id) {
//		return repository.findById(id).orElse(null);
//	}

//	public List<T> findByName(String name) {
//		return repository.findByName(name);
//	}
//}
