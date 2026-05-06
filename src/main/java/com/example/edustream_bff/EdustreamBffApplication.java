package com.example.edustream_bff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EdustreamBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdustreamBffApplication.class, args);
		log.info("EduStream BFF started");
		log.info("BFF is running at http://localhost:8081");

	}

}
