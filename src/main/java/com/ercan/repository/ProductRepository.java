package com.ercan.repository;

import com.ercan.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(
            value = "SELECT * FROM Product ORDER BY p_id",
            countQuery = "SELECT count(*) FROM Product",
            nativeQuery = true)
    Page<Product> findAllProductWithPagination(Pageable pageable);

}
