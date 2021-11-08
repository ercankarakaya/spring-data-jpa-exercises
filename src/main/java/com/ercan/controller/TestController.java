package com.ercan.controller;

import com.ercan.dto.responseDto.PageResponse;
import com.ercan.dto.responseDto.Response;
import com.ercan.dto.responseDto.TestEntityResponse;
import com.ercan.entity.TestEntity;
import com.ercan.exceptions.TestEntityNotFoundException;
import com.ercan.handler.ResponseHandler;
import com.ercan.repository.ProductRepository;
import com.ercan.service.criteria.MyCriteriaApiService;
import com.ercan.service.TestEntityService;
import com.ercan.service.specification.FilterSpecification;
import com.ercan.service.specification.MySpecificationService;
import com.ercan.service.specification.SearchCriteria;
import com.ercan.utilities.CustomDateUtil;
import com.ercan.utilities.MessageUtil;
import com.ercan.utilities.PageUtil;
import com.google.gson.JsonObject;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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
    @Autowired
    private MySpecificationService specificationService;


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

    @PostMapping(value = "/save")
    public ResponseEntity saveTestEntity(@RequestBody TestEntity testEntity) {
        try {
            //testEntity.setProduct(testEntityService.findProductByTestEntity2());
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

    //Filter with Specification
    @GetMapping("/searchByTestEntityParameters")
    public ResponseEntity searchByTestEntityParameters(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(specificationService.searchByTestEntityParameters(testEntity));
    }

//    @GetMapping("/testEntities")
//    public ResponseEntity<?> search(@RequestParam(value = "search") String search) {
//        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        List<SearchCriteria> params = Lists.newArrayList();
//        while (matcher.find()) {
//            params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
//        }
//
//        FilterSpecification spec = new FilterSpecification(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
//        return ResponseEntity.ok(testEntityService.findAll(spec));
//    }

    @GetMapping("/entitiesByIdAndName")
    public ResponseEntity<?> searchByIdAndName(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "20") int size,
                                               @RequestParam(value = "orders", required = false) String orders,
                                               @RequestParam(value = "id") int id,
                                               @RequestParam(value = "name") String name) {
        PageResponse<TestEntity> response = new PageResponse();
        Pageable pageable = PageUtil.getPageable(size, page, orders);
        return ResponseEntity.ok(specificationService.searchTestEntityByIdAndName(pageable, id, name));
    }

    /**
     * Example Requests :
     * url: localhost:7777/test/entitiesByName?page=1&size=20&name=Ali&orders=createdDate%7CASC
     * url: localhost:7777/test/entitiesByName?page=1&size=20&name=Ali&orders=
     */
    @GetMapping("/entitiesByName")
    public ResponseEntity<?> searchByName(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "20") int size,
                                          @RequestParam(value = "name") String name,
                                          @RequestParam(value = "orders", required = false) String orders) {
        PageResponse<TestEntity> response = new PageResponse();
        Pageable pageable = PageUtil.getPageable(size, page, orders);
        return ResponseEntity.ok(specificationService.searchTestEntityByName(pageable, name));
    }



}
