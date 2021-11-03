package com.ercan.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private final static String ERROR = "ERROR";
    private final static String INFO = "INFO";

    @Autowired
    private MessageSource messageSource;


    public String errorMessage(String message) {
        return ERROR + " : " + messageSource.getMessage(message, null, Locale.ENGLISH);
    }

    public String infoMessage(String message) {
        return INFO + " : " + messageSource.getMessage(message, null, Locale.ENGLISH);
    }

}
