package com.jamir.hubvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jamir.hubvendas.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query(value = "SELECT * FROM product LIMIT :limit OFFSET :offset", nativeQuery = true)
	public List<Product> findWithPagination(int offset, int limit);
}
