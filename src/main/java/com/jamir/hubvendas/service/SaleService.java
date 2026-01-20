package com.jamir.hubvendas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamir.hubvendas.model.Sale;
import com.jamir.hubvendas.repository.SaleRepository;

@Service
public class SaleService {
    @Autowired
    private SaleRepository sr;
    public List<Sale> findSalesWithPagination(int offset, Long userId, int limit){
        return sr.findSalesWithPagination(offset, userId, limit);
    }
    public Optional<Sale> create(Sale sale){
        return Optional.of(sr.save(sale));
    }
    public Optional<Sale> findById(Long idsale){
        return sr.findById(idsale);
    }
    public Optional<Sale> update(Sale sale){
    	return sr.findById(sale.getIdsale()).map(saleFound ->{    		
    		saleFound.setStatus(sale.getStatus());
    		return sr.save(saleFound);
    	});
    }
    public List<Sale> findByCreatedAtAndUser(LocalDateTime start, LocalDateTime end, Long iduser){
    	return sr.findByCreatedAtAndUser(start, end, iduser);
    }
}
