package com.jamir.hubvendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamir.hubvendas.model.Customer;
import com.jamir.hubvendas.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository cr;

    public List<Customer> findAll(){
        return cr.findAll();
    }

    public Optional<Customer> findById(Long id){
        return cr.findById(id);
    }
    
    public List<Customer> findWithPagination(int offset, Long userId){
        return cr.findCustomerWithPagination(offset, userId, 5);
    }
    public Optional<Customer> create(Customer c){
        return Optional.of(cr.save(c));
    }
    public Optional<Customer> update(Long idcustomer, Customer updatedUser){
        return cr.findById(idcustomer).map(customerFound ->{
            customerFound.setCpf(updatedUser.getCpf());
            customerFound.setDescription(updatedUser.getDescription());
            customerFound.setEmail(updatedUser.getEmail());
            customerFound.setName(updatedUser.getName());
            customerFound.setPhone(updatedUser.getPhone()); 
            return cr.save(customerFound);
        });
    }
    public Optional<Customer> delete(Long idcustomer){
        return cr.findById(idcustomer).map(customerFound ->{
            cr.delete(customerFound);
            return customerFound;
        });
    }
}
