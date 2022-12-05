package ch.swisscom.prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ch.swisscom") // Enables to find the REST Controller bean in 'com.swisscom.controller'
@EntityScan("ch.swisscom.entity")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
