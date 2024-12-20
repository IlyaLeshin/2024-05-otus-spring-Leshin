package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Application.class);

		SpringApplication.run(Application.class, args);

		logger.info("http://localhost:8080/ - ссылка для быстрого перехода к проврке приложения");
	}

}
