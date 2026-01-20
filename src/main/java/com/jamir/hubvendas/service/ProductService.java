package com.jamir.hubvendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamir.hubvendas.model.Product;
import com.jamir.hubvendas.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository pr;

    public Optional<Product> details(Long idproduto) {
        return pr.findById(idproduto);
    }

    public Optional<Product> create(Product product) {        
        return Optional.of(pr.save(product));
    }

    public List<Product> findAll(){
        return pr.findAll();
    }
    
    public List<Product> findWithPagination(int offset, int limit){
        return pr.findWithPagination(offset, limit);
    }
    
    public Optional<Product> findById(Long id){
        return pr.findById(id);
    }

    public Optional<Product> update(Long idproduct, Product requestProduct) {
        return pr.findById(idproduct).map(productFound -> {
            productFound.setName(requestProduct.getName());
            productFound.setPrice(requestProduct.getPrice());
            productFound.setQuantity(requestProduct.getQuantity());
            productFound.setDescription(requestProduct.getDescription());
            productFound.setImgUrl(requestProduct.getImgUrl());
            return pr.save(productFound);
        });        
    }

    public Optional<Product> delete(Long idproduct) {
        return pr.findById(idproduct).map(productFound ->{
            pr.delete(productFound);
            return productFound;
        });
    }
}
