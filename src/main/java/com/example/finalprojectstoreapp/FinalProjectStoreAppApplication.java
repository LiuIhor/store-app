package com.example.finalprojectstoreapp;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class FinalProjectStoreAppApplication {

    public static void main(String[] args) {
        log.info("Start app");
        SpringApplication.run(FinalProjectStoreAppApplication.class, args);
    }
}