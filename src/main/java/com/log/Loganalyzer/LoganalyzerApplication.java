package com.log.Loganalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoganalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoganalyzerApplication.class, args);
        System.out.println("Please make sure to have logs in '[2024-10-18 12:34:56] [INFO]: Application started' in this format.");
	}
}
