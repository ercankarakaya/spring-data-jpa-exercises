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

        //work1();
        //fileNameList();
        //work2();

    }

    public static void fileNameList() throws FileNotFoundException {
        for (int i = 0; i < downloadMulti().length; i++) {
            System.out.println(downloadMulti()[i].getName());
        }
    }

    @XmlMimeType("*/*")
    public static DataHandler[] downloadMulti() throws FileNotFoundException {
        final File[] files = getFileDepository().listFiles();
        DataHandler[] handlers = new DataHandler[files.length];

        for (int i = 0, j = files.length; i < j; i++) {
            final String fileName = files[i].getName();
            handlers[i] =
                    new DataHandler(new FileDataSource(files[i])) {
                        public String getName() {
                            return fileName;
                        }
                    };
        }
        return handlers;
    }

    private static File getFileDepository() {
        return new File(System.getProperty("user.dir"));
    }

    public static void work1() throws UnsupportedEncodingException {
    }

    public static void work2() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("ingwords.pdf"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Put data in your baos

            DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/octet-stream");
            DataHandler dataHandler = new DataHandler(aAttachment);
            System.out.println(dataHandler.getName());
            baos.writeTo(fos);
        } catch (IOException ioe) {
            // Handle exception here
            ioe.printStackTrace();
        } finally {
            fos.close();
        }
    }

}
