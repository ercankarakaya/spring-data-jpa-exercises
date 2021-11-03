package com.ercan.repository;

import com.ercan.dto.responseDto.OrderResponse;
import com.ercan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * This method fetches the product name and customer name.
     * Bu method customer ve product tablosunu birleştirip ilgili name'leri getirir.
     *
     * @return
     */
    @Query("SELECT new com.ercan.dto.responseDTO.OrderResponse(c.name,p.productName)" +
            " FROM Customer c JOIN c.products p")
    public List<OrderResponse> getJoinInformation();

    /**
     * Erkek müşterileri getir.
     */
    @Query("SELECT new com.ercan.dto.responseDTO.OrderResponse(c.name)" +
            " FROM Customer c WHERE c.gender = 'male'")
    public List<OrderResponse> getMaleCustomers();

    /**
     * Erkek müşterileri native SQL query ile getirir.
     */
    @Query(value = "SELECT * FROM Customer c" +
            " WHERE c.gender='male'",nativeQuery = true)
    public List<Customer> getMaleCustomersWithNativeSQL();

    @Query("select new com.ercan.dto.responseDTO.OrderResponse(c.name) from Customer c where c.name=:name")
    public OrderResponse getCustomerByName(@PathParam("name") String name);


    @Query("select c.name from Customer c where c.name=:name")
    String getByName(@PathParam("name") String name);


}
