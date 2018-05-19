package com.caesar_84mx.springbootRestApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootRestApiApplication {
    //TODO: revise jwt secret in application.properties
    //TODO: add Lombok library, 'cause in Java 10 it is not fully supported in Maven, should be downloaded edge version from site
    //TODO: revise avatar url: might have specific parameters (protocol, host etc.)
    //TODO: revise User entity class: might have specific fields, depending on your needs
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestApiApplication.class, args);
    }
}
