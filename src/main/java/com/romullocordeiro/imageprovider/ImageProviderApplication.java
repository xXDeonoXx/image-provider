package com.romullocordeiro.imageprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ImageProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProviderApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("5Urr0g4t35"));
	}

}

