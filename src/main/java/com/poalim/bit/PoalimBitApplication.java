package com.poalim.bit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PoalimBitApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoalimBitApplication.class, args);
    }

}
