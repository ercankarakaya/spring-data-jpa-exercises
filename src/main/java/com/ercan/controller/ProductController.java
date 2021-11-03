package com.ercan.controller;

import com.ercan.repository.CustomerRepository;
import com.ercan.repository.ProductRepository;
import com.ercan.service.MyCriteriaApiService;
import com.ercan.service.TestEntityService;
import com.ercan.service.TestEntityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private TestEntityService testEntityService;
    @Autowired
    private MyCriteriaApiService myCriteriaApiService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productRepo.findById(id));
    }

    @GetMapping("/getAllPagination")
    public ResponseEntity<?> findAllProductWithPagination(Pageable pageable) {
        return ResponseEntity.ok(productRepo.findAllProductWithPagination(pageable));
    }

    @GetMapping("/getCriteriaAllProduct")
    public ResponseEntity<?> getCriteriaAllProduct(){
        return ResponseEntity.ok(myCriteriaApiService.getProductAll());
    }

    @GetMapping("/getCriteriaProductNameList")
    public ResponseEntity<?> getCriteriaProductNameList(){
        return ResponseEntity.ok(myCriteriaApiService.getProductNameList());
    }

    @GetMapping("/getCriteriaProductNameList2")
    public ResponseEntity<?> getCriteriaProductNameList2(){
        return ResponseEntity.ok(myCriteriaApiService.getProductNameList2());
    }



}
