package com.ercan.entity;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String gender;
    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "cp_fk",referencedColumnName = "id")
    private List<Product> products;

    /**
     * NOT-1:"targetEntity",Collection taniminda generic yapi kullanilmazsa -
     bu durumda @OneToMany annotation’da targetEntity parametresi kullanilmalidir.
     * NOT-2:"Cascade", JPA standartıdır. Java sınıflarımızdaki ilişkilerin davranışlarını cascade niteliğini kullanarak
     ayarlarız. Örn; bir değer silinirse o veri ile ilişkili olan kayıtların etkilenmesini ya da etkilenmemesi işlemlerini
     Java nesneleri olarak yönetilmesini sağlamaktayız.Cascade bu davranışları Java nesneleri üzerinden yöneterek
     veritabanına ulaşmadan yormadan halleder.(Cascade türleri:PERSIST,MERGE,REMOVE,REFRESH,ALL)
     */



}
