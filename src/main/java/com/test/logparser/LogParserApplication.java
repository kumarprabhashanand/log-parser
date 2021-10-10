package com.test.logparser;

import com.test.logparser.service.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LogParserApplication {

	@Autowired
	private FileProcessor fileProcessor;

	public static void main(String[] args) {
		SpringApplication.run(LogParserApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			fileProcessor.processInputFileLogs(args);
			return;
		};
	}

}
