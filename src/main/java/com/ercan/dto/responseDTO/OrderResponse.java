package com.ercan.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//or -> @JsonIgnoreProperties({"price"})
public class OrderResponse {

    private String name;
    private String productName;
    private int price;

    public OrderResponse(String name, String productName) {
        this.name = name;
        this.productName = productName;
    }

    public OrderResponse(String name) {
        this.name = name;
    }
}
