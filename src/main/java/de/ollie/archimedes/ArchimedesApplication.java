package de.ollie.archimedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.ollie.archimedes.alexandrian.ArchimedesStarter;

@SpringBootApplication
public class ArchimedesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchimedesApplication.class, args);
		System.setProperty("java.awt.headless", "false");
		new ArchimedesStarter().startApplication(args);
	}

}