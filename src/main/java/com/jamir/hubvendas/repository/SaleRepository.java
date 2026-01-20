package com.jamir.hubvendas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jamir.hubvendas.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
    @Query(value = "SELECT * FROM sale WHERE user_id = :userId LIMIT :limit OFFSET :offset", nativeQuery = true)
    public List<Sale> findSalesWithPagination(int offset, Long userId, int limit);
    @Query(value = "SELECT * FROM sale WHERE created_at BETWEEN :start AND :end AND user_id = :iduser", nativeQuery = true)
    public List<Sale> findByCreatedAtAndUser(LocalDateTime start, LocalDateTime end, Long iduser);    
}
