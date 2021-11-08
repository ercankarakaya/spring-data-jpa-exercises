package com.ercan.service;

import com.ercan.entity.Product;
import com.ercan.entity.TestEntity;
import com.ercan.repository.ProductRepository;
import com.ercan.repository.TestEntityRepository;
import com.ercan.service.specification.FilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;


@Service
@Transactional
public class TestEntityServiceImpl implements TestEntityService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Autowired
    private ProductRepository productRepository;


    public TestEntity getById(int id) {
        return entityManager.find(TestEntity.class, id);
    }

    @Override
    public List<TestEntity> findAll(Specification specification) {
        return testEntityRepository.findAll(specification);
    }


    //@Transactional
    public void save(TestEntity testEntity) {
        entityManager.persist(testEntity);

    }

    public List getEntityProductIds() {
        List entityProductIds = new ArrayList();
        List<TestEntity> testEntityList = getAllTestEntity();
        testEntityList.stream().filter(e -> e.getProduct() != null).forEach(entity -> entityProductIds.add(entity.getProduct().getP_id()));
        return entityProductIds;
    }

    public Product findProductByTestEntity() {
        Product product = new Product();
        List entityProductIds = getEntityProductIds();
        if (entityProductIds.size() > 0) {
            product = (Product) entityManager.createNativeQuery("select * from Product p where p.p_id not in ?1", Product.class)
                    .setParameter(1, entityProductIds).getSingleResult();
        } else {
            product = productRepository.findAll(Sort.by(Sort.Direction.ASC)).get(0);
        }

        return product;
    }


    public List<TestEntity> getAllTestEntity() {
        return entityManager.createNativeQuery("select * from Test_Entity", TestEntity.class).getResultList();
    }

    public Product findProductByTestEntity2() {
        Product product = new Product();
        product = (Product) entityManager.createNativeQuery("select * from Product p where p.p_id not in " +
                "(select t.product_id from Test_Entity t where t.product_id is not null and t.id is not null) order by p.p_id", Product.class)
                .getResultList().stream().findFirst().orElse(null);

        if (getAllTestEntity().size() == 0) {
            product = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price")).stream().findFirst().orElse(null);
        }

        return product;
    }


// product = (Product) entityManager.createNativeQuery("select *,null from Product p where p.p_id" +
//  " not in (select t.product_id from Test_Entity t)", Product.class).setMaxResults(1).getSingleResult();


    @Override
    public String getProductNameByTestEntityJPQL(String name) {
        TypedQuery<String> query = entityManager.createQuery(
                "Select t.product.productName from TestEntity t where t.name=: testName", String.class)
                .setParameter("testName", name);

        return query.getSingleResult();
    }


}
