package com.ercan.service;

import com.ercan.entity.Product;
import com.ercan.entity.TestEntity;
import com.ercan.service.specification.FilterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TestEntityService {

    TestEntity getById(int id);

    List<TestEntity> findAll(Specification specification);

    void save(TestEntity testEntity);

    List getEntityProductIds();

    List<TestEntity> getAllTestEntity();

    Product findProductByTestEntity2();

   String getProductNameByTestEntityJPQL(String name);

}
