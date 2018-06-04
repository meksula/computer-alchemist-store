package com.computeralchemist.store;

import com.computeralchemist.store.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@SpringBootApplication
@EnableJpaAuditing
@Import(SecurityConfig.class)
public class ComputerAlchemistStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComputerAlchemistStoreApplication.class, args);
	}

}
