package com.api.Library;

import com.api.Library.Data.ArraylistDatabase;
import com.api.Library.Data.DatabaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static DatabaseRepository db = new ArraylistDatabase<>();

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
//        System.out.println("--- Library ---");
//        new Library();
    }

//    @Bean
//    public CommandLineRunner loadData() {
//        return (args) -> {
//            db = ;
//        };
//    }
}
