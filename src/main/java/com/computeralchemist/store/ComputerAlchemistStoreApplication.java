package com.computeralchemist.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@SpringBootApplication
@EnableJpaAuditing
public class ComputerAlchemistStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComputerAlchemistStoreApplication.class, args);
	}
}
