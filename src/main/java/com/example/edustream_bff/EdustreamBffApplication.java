package com.example.edustream_bff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(
		scanBasePackages = {
				"com.example.edustream_bff",
				"com.example.edustream_lib_security",
				"com.example.edustream_lib_common"
		}
)
public class EdustreamBffApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(EdustreamBffApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("EduStream BFF started");
		log.info("BFF is running at http://localhost:{}", port);
	}

}
