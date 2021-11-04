package com.ercan.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntityResponse {

    private String testName;
    private String productName;
    private String city;

    public TestEntityResponse(String testName, String productName) {
        this.testName = testName;
        this.productName = productName;
    }


}
