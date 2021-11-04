package com.ercan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import java.io.*;

@Slf4j
@SpringBootApplication
public class SpringDataJpaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringDataJpaApplication.class, args);
        log.info("SPRING BOOT PROJECT STARTED :)");
    }

}
