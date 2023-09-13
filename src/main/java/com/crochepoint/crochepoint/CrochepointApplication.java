package com.crochepoint.crochepoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.crochepoint.db.ConnectionSQLite;

@SpringBootApplication
@EnableScheduling
public class CrochepointApplication {

	public static void main(String[] args) {
		ConnectionSQLite.startDB();
		SpringApplication.run(CrochepointApplication.class, args);
	}

}
