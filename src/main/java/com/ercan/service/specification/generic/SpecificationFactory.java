package com.ercan.service.specification.generic;

import com.ercan.enums.SearchOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SpecificationFactory <T>{

     public Specification<T> isEqual(String key,Object arg){
         GenericSpecificationBuilder<T> builder=new GenericSpecificationBuilder<>();
         return builder.with(key, SearchOperation.EQUALITY, Collections.singletonList(arg)).build();
     }

    public Specification<T> isGreaterThan(String key, Comparable arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.GREATER_THAN, Collections.singletonList(arg)).build();
    }

    public Specification<T> isLessThan(String key, Comparable arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.LESS_THAN, Collections.singletonList(arg)).build();
    }

    public Specification<T> isIN(String key, Comparable arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.IN, Collections.singletonList(arg)).build();
    }

    public Specification<T> isLike(String key, Comparable arg) {
        GenericSpecificationBuilder<T> builder = new GenericSpecificationBuilder<>();
        return builder.with(key, SearchOperation.LIKE, Collections.singletonList(arg)).build();
    }


}
