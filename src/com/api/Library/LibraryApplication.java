package com.api.Library;

import com.api.Library.Business.model.Library;
import com.api.Library.Data.ArraylistDatabase;
import com.api.Library.Data.Database;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

    public static Database<String> db;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
//        System.out.println("--- Library ---");
//        new Library();
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            db = new ArraylistDatabase<>();
        };
    }
}
