package com.ercan.service;

import com.ercan.entity.Address;
import com.ercan.entity.Product;
import com.ercan.entity.TestEntity;
import org.aspectj.weaver.ast.Test;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Transactional
public class MyCriteriaApiService {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Product> getProductAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> from = criteriaQuery.from(Product.class);
        criteriaQuery.select(from);

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        List<Product> results = query.getResultList();
        return results;
    }

    public List<String> getProductNameList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> from = criteriaQuery.from(Product.class);
        criteriaQuery.select(from);

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        List<Product> results = query.getResultList();
        List<String> productNameList = Lists.newArrayList();
        results.forEach(product -> {
            productNameList.add(product.getProductName());
        });
        return productNameList;
    }

    public List<String> getProductNameList2() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(productRoot.get("productName")).distinct(true);
        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    // used Tuple:: Tuple objesi üzerinden get methodunu kullanarak ilgili alanlara erişim sağliyoruz.
    public List<Tuple> getTestAndProductNameList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class); // criteriaBuilder.createTupleQuery();
        Root<TestEntity> root = criteriaQuery.from(TestEntity.class);
        criteriaQuery.select(criteriaBuilder.tuple(root.get("name"), root.get("createdDate"), root.get("product").get("productName")));
        //criteriaQuery.multiselect(root.get("name"), root.get("createdDate"),root.get("product").get("productName"));
        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /*
     * used construct
     * get all test name and product name
     */
    public List<TestEntity> getTestAndProductNameList2() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TestEntity> criteriaQuery = criteriaBuilder.createQuery(TestEntity.class); // criteriaBuilder.createTupleQuery();
        Root<TestEntity> root = criteriaQuery.from(TestEntity.class);
        criteriaQuery.select(criteriaBuilder.construct(TestEntity.class, root.get("name"), root.get("createdDate"), root.get("product").get("productName")));
        TypedQuery<TestEntity> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /*
     * with Criteria API
     * get product name by test entity name
     */
    public String getProductNameByTestEntityCriteria(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<TestEntity> root = criteriaQuery.from(TestEntity.class);
        criteriaQuery.select(root.get("product").get("productName")).where(
                criteriaBuilder.equal(root.get("name"), name));
        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    /*
     * Criteria Join
     * get product name by test entity name
     */
    public String getProductNameByTestEntityCriteriaJoin(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<TestEntity> root = criteriaQuery.from(TestEntity.class);
        Join<TestEntity, Product> productJoin = root.join("product");
        criteriaQuery.select(productJoin.get("productName")).where(criteriaBuilder.equal(root.get("name"), name));
        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    /**
     * Criteria Join
     * get test name by city
     */
    public List<String> getNameOfTestByCityCriteriaJoin(String productName, String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<TestEntity> root = criteriaQuery.from(TestEntity.class);

        Join<TestEntity, Product> productJoin = root.join("product");
        System.out.println("getNameOfTestByCityJoin method root size:" + root.getJoins().size());
        Join<TestEntity, Address> addressJoin = root.join("address");
        System.out.println("getNameOfTestByCityJoin method  root size:" + root.getJoins().size());

        criteriaQuery.select(root.get("name")).where(
                criteriaBuilder.equal(productJoin.get("productName"), productName),
                criteriaBuilder.equal(addressJoin.get("city"), city));

        TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }

    /**
     * Criteria Join
     *
     */
    public List<Tuple> getNameAndProductNameCriteriaJoin() {

        return null;
    }


}
