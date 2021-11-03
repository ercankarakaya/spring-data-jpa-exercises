package com.ercan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "product"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Calendar createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "p_id")
    //@JsonIgnore
    //@JsonProperty("entityProduct")
    private Product product;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @PrePersist
    public void onCreaete() {
        setCreatedDate(Calendar.getInstance());
    }

    private transient String productName;
    private transient String city;

    public TestEntity(String name, Calendar createdDate, String productName) {
        this.name = name;
        this.createdDate = createdDate;
        this.productName=productName;
    }

}
