package com.api.Library;

import com.api.Library.Data.ArraylistDatabase;
import com.api.Library.Data.DatabaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public DatabaseRepository databaseRepository() {
        return new ArraylistDatabase<>();
    }
}

