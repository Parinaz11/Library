package com.api.Library;

import com.api.Library.model.Library;
import com.api.Library.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
//        System.out.println("--- Library ---");
//        new Library();
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            Library.populate_BooksUsersReserves();
        };
    }
}
