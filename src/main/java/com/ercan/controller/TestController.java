package com.ercan.controller;

import com.ercan.dto.responseDto.Response;
import com.ercan.dto.responseDto.TestEntityResponse;
import com.ercan.entity.TestEntity;
import com.ercan.exceptions.TestEntityNotFoundException;
import com.ercan.handler.ResponseHandler;
import com.ercan.repository.ProductRepository;
import com.ercan.service.criteria.MyCriteriaApiService;
import com.ercan.service.TestEntityService;
import com.ercan.utilities.CustomDateUtil;
import com.ercan.utilities.MessageUtil;
import com.google.gson.JsonObject;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestEntityService testEntityService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private MyCriteriaApiService myCriteriaApiService;


    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getTestEntityById(@PathVariable int id) {
        try {
            TestEntity testEntity = testEntityService.getById(id);
            if (testEntity == null) {
                return ResponseEntity.ok(messageUtil.errorMessage("error.testEntity.not.found"));
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
            return ResponseEntity.ok(messageUtil.infoMessage("info.saved.success"));
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
    public ResponseEntity getNameOfTestByCityJoin(@RequestBody TestEntityResponse testEntityResponse) {
        try {
            return ResponseHandler.jsonGenerateResponse(messageUtil.infoMessage("info.data.listed"), HttpStatus.OK,
                    myCriteriaApiService.getNameOfTestByCityCriteriaJoin(testEntityResponse.getProductName(), testEntityResponse.getCity()));
        } catch (Exception e) {
            return ResponseHandler.jsonGenerateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        //return ResponseEntity.ok(myCriteriaApiService.getNameOfTestByCityCriteriaJoin(testEntityResponse.getProductName(), testEntityResponse.getCity()));
    }


    @GetMapping("/getNameAndProductNameCriteriaJoin")
    public ResponseEntity getNameAndProductNameCriteriaJoin() {

        try {
            return ResponseHandler.jsonGenerateResponse(messageUtil.infoMessage("info.data.listed"), HttpStatus.OK,
                    myCriteriaApiService.getNameAndProductNameCriteriaJoin2());
        } catch (Exception e) {
            return ResponseHandler.jsonGenerateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    /**
     * NOTE:Added spring.mvc.converters.preferred-json-mapper=gson the application.properties for JsonObject.
     */
    @GetMapping("/getNameAndProductNameCriteriaJoin2")
    public ResponseEntity getNameAndProductNameCriteriaJoin2() {

        List<JsonObject> entities = new ArrayList<JsonObject>();
        myCriteriaApiService.getNameAndProductNameCriteriaJoin().forEach(t -> {
            JsonObject entity = new JsonObject();
            entity.addProperty("testName", (String) t.get(0));
            entity.addProperty("productName", (String) t.get(1) == null ? "" : (String) t.get(1));
            entities.add(entity);
        });

        return ResponseEntity.ok(entities);
    }

    @GetMapping("/getNameAndProductNameCriteriaJoin3")
    public ResponseEntity getNameAndProductNameCriteriaJoin3() {
        try {
            Optional<List<TestEntityResponse>> testEntityResponses = myCriteriaApiService.getNameAndProductNameCriteriaJoin2();
            if (testEntityResponses.isEmpty()) {
                return new ResponseEntity<Object>(new Response(messageUtil.errorMessage("error.testEntity.not.found"),
                        HttpStatus.MULTI_STATUS.value(), testEntityResponses), HttpStatus.MULTI_STATUS);
            }
            return ResponseEntity.ok(new Response(messageUtil.infoMessage("info.data.listed"), HttpStatus.OK.value(), testEntityResponses));
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.MULTI_STATUS);
        }
    }

    @GetMapping("/getNameAndProductNameCriteriaJoin4")
    public ResponseEntity getNameAndProductNameCriteriaJoin4() {

        try {
            Optional<List<TestEntityResponse>> testEntityResponses = myCriteriaApiService.getNameAndProductNameCriteriaJoin2();

            return ResponseHandler.jsonGenerateResponse(messageUtil.infoMessage("info.data.listed"), HttpStatus.OK,
                    testEntityResponses.orElseThrow(() -> new TestEntityNotFoundException(messageUtil.errorMessage("error.testEntity.not.found"))));

        } catch (Exception e) {
            return ResponseHandler.jsonGenerateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    


}
