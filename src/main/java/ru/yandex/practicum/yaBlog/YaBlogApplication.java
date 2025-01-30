package ru.yandex.practicum.yaBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.yaBlog.model.BlogsModel;

@SpringBootApplication
public class YaBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(YaBlogApplication.class, args);
	}

}
