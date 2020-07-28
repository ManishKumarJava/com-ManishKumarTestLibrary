package com.example.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LibraryApplication {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) {
        LOG.info("Staring LibraryApplication");
        SpringApplication.run(LibraryApplication.class, args);
        LOG.info("LibraryApplication Started");
    }

}
