package com.example.demo;

/**
 * This class file is called when the application loads.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
	/**
	 * 
	 * @param args
	 *            : command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
