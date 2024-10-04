package ru.otus.hw;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Application.class);

		SpringApplication.run(Application.class, args);

		logger.info("http://localhost:8080/ - ссылка для быстрого перехода к проврке приложения");
	}

}