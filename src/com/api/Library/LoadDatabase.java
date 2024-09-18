package com.api.Library;

import com.api.Library.Data.ArraylistDatabase;
import com.api.Library.Data.DatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public DatabaseRepository databaseRepository() {
        return new ArraylistDatabase<>();
    }

    @Bean
    public CommandLineRunner initDatabase(DatabaseRepository databaseRepository) {
        return args -> {
            log.info("Initializing the database...");
            // Example: databaseRepository.save(new YourEntity(...));
        };
    }
}