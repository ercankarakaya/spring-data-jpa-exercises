package com.ercan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private int p_id;
    private String productName;
    private int qty;
    private Double price;

/*
    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("CREATED_DATE DESC")
    //@JsonIgnore
    private List<TestEntity> testEntity;
*/

}
