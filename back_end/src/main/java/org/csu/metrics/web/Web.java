package org.csu.metrics.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Web {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Web.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "9000"));
        app.run(args);
    }
}
