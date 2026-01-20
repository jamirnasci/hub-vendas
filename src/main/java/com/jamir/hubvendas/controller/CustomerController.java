package com.jamir.hubvendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamir.hubvendas.config.UserPrincipal;
import com.jamir.hubvendas.model.Customer;
import com.jamir.hubvendas.service.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService cs;
    @GetMapping("/customer")
    public ModelAndView customer(@RequestParam(name = "offset", required = false) Integer offset, @AuthenticationPrincipal UserPrincipal user){
        offset = (offset == null) ? 0 : offset;
        List<Customer> customers = cs.findWithPagination(offset, user.getId());
        ModelAndView mv = new ModelAndView("customer/customerSection");
        mv.addObject("customers", customers);
        return mv;
    }
    @PostMapping("/customer/create")
    public String customer(Customer customer, @AuthenticationPrincipal UserPrincipal user){
        customer.setUser(user.getUser());
        return cs.create(customer).map(createdCustomer -> {
            return "redirect:/customer";            
        }).orElseGet(()->{
            return "redirect:/customer?err=Falha ao cadastrar cliente";
        });
    }
    @PostMapping("/customer/update")
    public String update(Customer customer, @AuthenticationPrincipal UserPrincipal user){
        return cs.update(customer.getIdcustomer(), customer).map(updatedUser ->{
            return "redirect:/customer";
        }).orElseGet(() -> {
            return "redirect:/customer?err=Falha ao atualizar cliente";
        });
    }
    @GetMapping("/customer/update/{id}")
    public ModelAndView update(@PathVariable(name = "id", required = true) Long id){
        return cs.findById(id).map(customerFound ->{
            ModelAndView mv = new ModelAndView("customer/updateForm");
            mv.addObject("customer", customerFound);
            return mv;
        }).orElseGet(()->{
            return new ModelAndView("redirect:/customer?err=Falha ao encontrar cliente");
        });
    }
    @GetMapping("/customer/delete/{id}")
    public String delete(@PathVariable(name = "id", required = true) Long id){
        return cs.delete(id).map(removedCustomer -> {
            return "redirect:/customer";
        }).orElseGet(()->{
            return "redirect:/customer?err=Falha ao remover cliente";
        });
    }
}
