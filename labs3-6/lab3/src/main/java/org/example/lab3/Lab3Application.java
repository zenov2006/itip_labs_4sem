package org.example.lab3;

import org.example.lab3.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class Lab3Application {
	public static void main(String[] args) {
		SpringApplication.run(Lab3Application.class, args);
	}
}