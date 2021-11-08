package com.ercan.entity;

import com.ercan.utilities.CustomDateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "product"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TestEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Long no;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "p_id")
    //@JsonIgnore
    //@JsonProperty("entityProduct")
    private Product product;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

//    @PrePersist
//    public void onCreaete() {
//        setCreatedDate(Calendar.getInstance());
//    }

    private transient String productName;
    private transient String city;
    private transient Calendar maxCreatedDate;

    public TestEntity(String name, Calendar createdDate, String productName) {
        this.name = name;
        this.createdDate = createdDate;
        this.productName=productName;
    }

}
