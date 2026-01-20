package com.jamir.hubvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jamir.hubvendas.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @Query(value = "SELECT * FROM customer WHERE user_id = :userId LIMIT :limit OFFSET :offset", nativeQuery = true)
    public List<Customer> findCustomerWithPagination(int offset, Long userId, int limit);
}
