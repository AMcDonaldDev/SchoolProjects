package org.cmsc495.bpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Here, we declare our application as a Spring Boot application which gives 
 * Spring Boot the control it needs to launch a full-scale web server on our
 * behalf. 
 */
@SpringBootApplication()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
