package com.ercan.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> jsonGenerateResponse(String messageDetails,
                                                              HttpStatus httpStatus,
                                                              Object objResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Message", messageDetails);
        map.put("status", httpStatus.value());
        map.put("data", objResponse);
        return new ResponseEntity<Object>(map, httpStatus);
    }

}
