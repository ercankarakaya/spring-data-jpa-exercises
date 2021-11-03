package com.ercan.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue
    private String id;
    private String city;

}
