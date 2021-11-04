package com.ercan.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> jsonGenerateResponse(String messageDetails,
                                                              HttpStatus httpStatus,
                                                              Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", messageDetails);
        map.put("status", httpStatus.value());
        map.put("data", obj);
        return new ResponseEntity<Object>(map, httpStatus);
    }

}
