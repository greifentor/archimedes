package de.ollie.archimedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.ollie")
public class ArchimedesApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(ArchimedesApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(ArchimedesApplication.class, args);
//		context.getBean(ArchimedesStarter.class).startApplication(args);
	}

}