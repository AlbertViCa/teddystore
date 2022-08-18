package com.teddystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("dev")
public class TeddyStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeddyStoreApplication.class, args);
    }

}
