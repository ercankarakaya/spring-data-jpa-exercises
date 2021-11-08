package com.ercan.service.specification;

import com.ercan.entity.Address;
import com.ercan.entity.Product;
import com.ercan.entity.TestEntity;
import com.ercan.repository.ProductRepository;
import com.ercan.repository.TestEntityRepository;
import com.ercan.service.specification.generic.GenericSpecificationBuilder;
import com.ercan.service.specification.generic.SpecificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MySpecificationService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestEntityRepository testEntityRepository;
    @Autowired
    private SpecificationFactory<TestEntity> testEntitySpecificationFactory;


    //Product Filter by product name
    public List<Product> findProductNameSpec() {
        FilterSpecification spec = new FilterSpecification(new SearchCriteria("productName", ":", "Laptop"));
        List<Product> results = productRepository.findAll(spec);
        return results;
    }

    //Product Filter by product name and price
    public List<Product> findProductByNameAndPriceSpec() {
        FilterSpecification spec1 = new FilterSpecification(new SearchCriteria("productName", ":", "TV"));
        FilterSpecification spec2 = new FilterSpecification(new SearchCriteria("price", ">", null)); //value:5000
        List<Product> results = productRepository.findAll(Specification.where(spec1).and(spec2));
        //List<Product> results = productRepository.findAll(Specification.where(spec1).or(spec2));
        return results;
    }


    //TestEntity filter by all TestEntity properties
    public List<TestEntity> searchByTestEntityParameters(TestEntity testEntity) {
        List<TestEntity> testEntityList = testEntityRepository.findAll(new Specification<TestEntity>() {
            @Override
            public Predicate toPredicate(Root<TestEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                Optional.ofNullable(testEntity.getName()).ifPresent(item -> predicates.add(
                        builder.and(builder.equal(root.get("name"), item))));
                Optional.ofNullable(testEntity.getCreatedDate()).ifPresent(item -> predicates.add(
                        builder.and(builder.lessThanOrEqualTo(root.get("createdDate"), item))));
                Optional.ofNullable(testEntity.getProduct()).ifPresent(item -> predicates.add(
                        builder.and(builder.equal(root.get("product").get("productName"), item.getProductName()))));
                Optional.ofNullable(testEntity.getAddress()).ifPresent(item -> predicates.add(
                        builder.and(builder.equal(root.get("address").get("city"), item.getCity()))));

                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

        return testEntityList;
    }

    //Using Generic Specification Filter by id and name.
    public Page<TestEntity> searchTestEntityByIdAndName(Pageable pageable, int id, String name) {
        GenericSpecificationBuilder<TestEntity> builder = new GenericSpecificationBuilder<>();
        Optional.ofNullable(id).ifPresent(item->builder.with(testEntitySpecificationFactory.isEqual("id",id)));
        Optional.ofNullable(name).ifPresent(item->builder.with(testEntitySpecificationFactory.isLike("name",name)));
        return testEntityRepository.findAll(builder.build(), pageable);
    }

    //Using Generic Specification Filter
    public Page<TestEntity> searchTestEntityByName(Pageable pageable, String name) {
        GenericSpecificationBuilder<TestEntity> builder = new GenericSpecificationBuilder<>();
        Optional.ofNullable(name).ifPresent(item->builder.with(testEntitySpecificationFactory.isLike("name",item)));
        return testEntityRepository.findAll(builder.build(), pageable);
    }



}



