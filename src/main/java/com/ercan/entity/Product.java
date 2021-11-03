package com.ercan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product {

    @Id
    private int p_id;
    private String productName;
    private int qty;
    private int price;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("CREATED_DATE DESC")
    @JsonIgnore
    private List<TestEntity> testEntity;

}
