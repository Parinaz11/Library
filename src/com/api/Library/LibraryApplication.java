package com.api.Library;

import com.api.Library.model.Library;
import com.api.Library.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
    public static void main(String[] args) {
        System.out.println("--- com.api.Library.model.Library ---");
//        new Library();

        SpringApplication.run(LibraryApplication.class, args);
    }
}
