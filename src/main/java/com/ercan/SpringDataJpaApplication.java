package com.ercan;

import com.sun.istack.ByteArrayDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.bind.annotation.XmlMimeType;
import java.io.*;

@SpringBootApplication
public class SpringDataJpaApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(SpringDataJpaApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringDataJpaApplication.class, args);

        LOGGER.info("SPRING BOOT PROJECT STARTED :)");

    }

}
