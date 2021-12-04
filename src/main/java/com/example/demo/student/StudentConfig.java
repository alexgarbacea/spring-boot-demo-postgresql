package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student Alex = new Student(
                    "Alex",
                    "alex@mail.ro",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student John = new Student(
                    "John",
                    "john@mail.ro",
                    LocalDate.of(2007, Month.FEBRUARY, 15)
            );

            repository.saveAll(
                    Arrays.asList(Alex, John)
            );
        };
    }
}
