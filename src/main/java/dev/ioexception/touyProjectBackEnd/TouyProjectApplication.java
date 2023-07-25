package dev.ioexception.touyProjectBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TouyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TouyProjectApplication.class, args);
	}

}
