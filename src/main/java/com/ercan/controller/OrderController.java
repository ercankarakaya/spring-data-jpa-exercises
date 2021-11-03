package com.ercan.controller;

import com.ercan.dto.requestDTO.OrderRequest;
import com.ercan.dto.responseDTO.OrderResponse;
import com.ercan.entity.Customer;
import com.ercan.repository.CustomerRepository;
import com.ercan.repository.ProductRepository;
import com.ercan.service.TestEntityServiceImpl;
import com.ercan.utilities.InfoMessageUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final static Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private TestEntityServiceImpl testEntityService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            customerRepo.save(orderRequest.getCustomer());
            logger.info("Başarıyla kaydedildi->" + orderRequest.getCustomer());
            return ResponseEntity.ok("Saved Successfully..");
        } catch (Exception ex) {
            //logger.error("Hata->",ex);
            return new ResponseEntity<>("Beklenmedik hata:\n" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // return new ResponseEntity<String>("Saved Successfully..", HttpStatus.OK);
        // return new ResponseEntity<Customer>(orderRequest.getCustomer(), HttpStatus.OK);
    }

    @GetMapping("/findAllOrders")
    public ResponseEntity<List<Customer>> findAllOrders() {
        List<Customer> customers = customerRepo.findAll();
        return ResponseEntity.ok(customers);
        // return ResponseEntity.status(HttpStatus.OK).body("Tüm Siparişler:\n"+customers);
    }

    @GetMapping("/getInfo")
    public ResponseEntity<List<OrderResponse>> getJoinInformation() {
        List<OrderResponse> orderResponses = customerRepo.getJoinInformation();
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/getMales")
    public ResponseEntity<List<OrderResponse>> getMaleCustomers() {
        List<OrderResponse> orderResponses = customerRepo.getMaleCustomers();
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/getMalesNative")
    public ResponseEntity<List<Customer>> getMaleCustomersWithNativeSQL() {
        List<Customer> customers = customerRepo.getMaleCustomersWithNativeSQL();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getSortByASC")
    public ResponseEntity<List<Customer>> getSortByASC() {
        /* create one TestEntity object */
//        TestEntity testEntity = new TestEntity();
//        testEntity.setName("Ercan");
//        testEntityService.save(testEntity);

        /* get max price product and update */
//        Product product = productRepo.findAll(Sort.by(Sort.Direction.DESC, "price")).get(0);
//        product.setTestEntity(testEntity);
//        productRepo.save(product);

        List<Customer> customers = customerRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
        //veya List<Customer> customers = customerRepo.findAll(Sort.by("name"));
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/getCustomerByName")
    public ResponseEntity getCustomerByName(@RequestBody String name) {
        OrderResponse orderResponse = customerRepo.getCustomerByName(name);
        if (orderResponse == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(InfoMessageUtility.CUSTOMER_NAME_NOT_FOUND);
        }
        return ResponseEntity.ok(orderResponse);
    }


}
