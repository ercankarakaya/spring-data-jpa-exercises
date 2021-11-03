package com.ercan.service;

import com.ercan.entity.Product;
import com.ercan.entity.TestEntity;

import java.util.List;

public interface TestEntityService {

    TestEntity getById(int id);

    void save(TestEntity testEntity);

    List getEntityProductIds();

    List<TestEntity> getAllTestEntity();

    Product findProductByTestEntity2();

   String getProductNameByTestEntityJPQL(String name);

}
