package com.ercan.controller;

import com.ercan.entity.TestEntity;
import com.ercan.repository.ProductRepository;
import com.ercan.service.MyCriteriaApiService;
import com.ercan.service.TestEntityService;
import com.ercan.service.TestEntityServiceImpl;
import com.ercan.utilities.CustomDateUtil;
import com.ercan.utilities.MessageUtility;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestEntityService testEntityService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private MessageUtility messageUtility;
    @Autowired
    private MyCriteriaApiService myCriteriaApiService;


    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getTestEntityById(@PathVariable int id) {
        try {
            TestEntity testEntity = testEntityService.getById(id);
            if (testEntity == null) {
                return ResponseEntity.ok(messageUtility.errorMessage("error.testEntity.not.found"));
            }
            return ResponseEntity.ok(testEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(" unexpected error ->>" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity saveTestEntity(@RequestBody TestEntity testEntity) {
        try {
            testEntity.setProduct(testEntityService.findProductByTestEntity2());
            testEntityService.save(testEntity);
            return ResponseEntity.ok(messageUtility.infoMessage("info.saved.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(" unexpected error ->> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTestAndProductNameList")
    public ResponseEntity<?> getTestAndProductNameList() {

        List<String> stringList = Lists.newArrayList();
        myCriteriaApiService.getTestAndProductNameList().forEach(test -> {
            stringList.add((String) test.get(0) + " , " + CustomDateUtil.calendarToString((Calendar) test.get(1)) + " , " + test.get(2));
        });


        return ResponseEntity.ok(stringList);
    }

    @GetMapping("/getTestAndProductNameList2")
    public ResponseEntity<?> getTestAndProductNameList2() {

        List<String> stringList = Lists.newArrayList();
        myCriteriaApiService.getTestAndProductNameList2().forEach(test -> {
            stringList.add(test.getName() + " , " + CustomDateUtil.calendarToString(test.getCreatedDate()) + " , " + test.getProductName());
        });

        return ResponseEntity.ok(stringList);
    }

    @GetMapping("/getProductNameByTestEntityJPQL/{name}")
    public ResponseEntity getProductNameByTestEntityJPQL(@PathVariable String name) {
        return ResponseEntity.ok(testEntityService.getProductNameByTestEntityJPQL(name));
    }

    @GetMapping("/getProductNameByTestEntityCriteria/{name}")
    public ResponseEntity getProductNameByTestEntityCriteria(@PathVariable String name) {
        return ResponseEntity.ok(myCriteriaApiService.getProductNameByTestEntityCriteria(name));
    }

    @GetMapping("/getProductNameByTestEntityCriteriaJoin/{name}")
    public ResponseEntity getProductNameByTestEntityCriteriaJoin(@PathVariable String name) {
        return ResponseEntity.ok(myCriteriaApiService.getProductNameByTestEntityCriteriaJoin(name));
    }

    @GetMapping("/getNameOfTestByCityJoin")
    public ResponseEntity getNameOfTestByCityJoin(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(myCriteriaApiService.getNameOfTestByCityCriteriaJoin(testEntity.getProductName(),testEntity.getCity()));
    }


}
