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
import com.jamir.hubvendas.model.Product;
import com.jamir.hubvendas.model.Sale;
import com.jamir.hubvendas.service.CustomerService;
import com.jamir.hubvendas.service.ProductService;
import com.jamir.hubvendas.service.SaleService;

@Controller
public class SaleController {

    @Autowired
    private SaleService ss;
    @Autowired 
    private CustomerService cs;
    @Autowired
    private ProductService ps;

    @GetMapping("/sale")
    public ModelAndView sale(@RequestParam(name = "offset", required = false) Integer offset, @AuthenticationPrincipal UserPrincipal user){
        ModelAndView mv = new ModelAndView("sale/saleSection");
        List<Customer> customers = cs.findAll();
        List<Product> products = ps.findAll();
        offset = (offset == null) ? 0 : offset;
        List<Sale> sales = ss.findSalesWithPagination(offset, user.getId(), 5);

        mv.addObject("sales", sales);
        mv.addObject("customers", customers);
        mv.addObject("products", products);
        return mv;
    }
    @PostMapping("/sale/create")
    public String create(Sale sale, @AuthenticationPrincipal UserPrincipal user){
        sale.setUser(user.getUser());
        return ss.create(sale).map(createdSale -> {
            return "redirect:/sale";
        }).orElseGet(() ->{
            return "redirect:/sale?err=Falha ao cadastrar venda";
        });
    }
    @GetMapping("/sale/update/{id}")
    public ModelAndView update(@PathVariable(name = "id", required = true) Long idsale){
        ModelAndView mv = new ModelAndView("sale/updateForm");
        return ss.findById(idsale).map(saleFound ->{
            mv.addObject("sale", saleFound);
            mv.addObject("customer", saleFound.getCustomer());
            mv.addObject("product", saleFound.getProduct());
            return mv;
        }).orElseGet(()->{
            return new ModelAndView("redirect:/sale?err=Falha ao abrir venda");
        });
    }
    @PostMapping("/sale/update")
    public String update(Sale sale){
    	return ss.update(sale).map(updatedSale ->{
    		return "redirect:/sale";
    	}).orElseGet(()->{
    		return "redirect:/sale?err=Falha ao atualizar venda";
    	});
    }
}
