package com.EstudosJpaSpring.livariaapi;

import com.EstudosJpaSpring.livariaapi.model.Autor;
import com.EstudosJpaSpring.livariaapi.repository.AutorRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
public class LivariaapiApplication {

	public static void main(String[] args) {
        SpringApplication.run(LivariaapiApplication.class, args);


	}
}
