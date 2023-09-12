package com.crochepoint.crochepoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrochepointApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrochepointApplication.class, args);
	}

}
