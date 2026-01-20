package com.jamir.hubvendas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamir.hubvendas.model.Product;
import com.jamir.hubvendas.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    private ProductService ps;

    @GetMapping("/product/details/{id}")
    public ResponseEntity<Map<String, Object>> details(@PathVariable(name = "id") Long idproduto) {
        Map<String, Object> body = new HashMap<String, Object>();
        return ps.details(idproduto).map(product -> {
            body.put("msg", "Produto encontrado com sucesso");
            body.put("product", product);
            return ResponseEntity.ok(body);
        })
        .orElseGet(() -> {
            body.put("msg", "Falha ao encontrar produto");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        });
    }

    @GetMapping("/product")
    public ModelAndView product(@RequestParam(name = "offset", required = false) Integer offset) {    	
        ModelAndView mv = new ModelAndView("product/productSection");        
        offset = (offset == null) ? 0 : offset;
        mv.addObject("products", ps.findWithPagination(offset, 5));
        return mv;
    }

    @GetMapping("/product/update/{id}")
    public ModelAndView update(@PathVariable(name = "id") Long idproduct){
        ModelAndView mv = new ModelAndView();
        return ps.findById(idproduct).map(productFound ->{
            mv.setViewName("product/updateForm");
            mv.addObject("product", productFound);
            return mv;
        }).orElseGet(() -> {
            return new ModelAndView("redirect:/product");
        });
    }

    @PostMapping("/product/create")
    public String create(@Valid @ModelAttribute("product") Product product, BindingResult result) {        
        if(result.hasErrors()) {
        	System.out.println("Falha ao validar produto");
        	return "redirect:/product?err=true&msg=Erro nos dados fornecidos, tente novamente";
        }
    	return ps.create(product).map(createdProduct -> {
            return "redirect:/product";
        }).orElseGet(()->{            
            return "redirect:/product?err=true";
        });
    }

    @PostMapping("/product/update")
    public String update(Product product) {
        return ps.update(product.getIdproduct(), product).map(updatedProduct ->{
            return "redirect:/product";
        }).orElseGet(() ->{
            return "redirect:/product?err=Falha ao atualizar produto";
        });
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable(name = "id") Long idproduto) {
        return ps.delete(idproduto).map(removedProduct ->{
            return "redirect:/product";
        }).orElseGet(()->{
            return "redirect:/product?err=Falha ao remover produto";
        });
    }
}
