package cz.cvut.fit.anteater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.cvut.fit.anteater.repository.RaceRepository;

@SpringBootApplication
public class AnteaterApplication implements CommandLineRunner {

	@Autowired
	private RaceRepository raceRepo;

	public static void main(String[] args) {
		SpringApplication.run(AnteaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(raceRepo.findByName("Human"));
		System.out.println(raceRepo.findAll().stream().filter(r -> r.getSpeed() == 30).count());
	}
}
