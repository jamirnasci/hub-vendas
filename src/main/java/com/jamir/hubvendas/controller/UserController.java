package com.jamir.hubvendas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.jamir.hubvendas.model.User;
import com.jamir.hubvendas.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService us;

    @GetMapping("/user/details/{id}")
    public ResponseEntity<Map<String, Object>> details(@PathVariable(name = "id") Long iduser) {
        Map<String, Object> body = new HashMap<String, Object>();
        return us.details(iduser)
        .map(userFound ->{
            body.put("msg", "Usuário encontrado com sucesso");
            body.put("user", userFound);    
            return ResponseEntity.ok(body);
        })
        .orElseGet(() -> {
            body.put("msg", "Falha ao encontrar usuário");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        });
    }

    @GetMapping("/create-account")
    public String create(){
        return "createAccount";
    }

    @PostMapping("/user/create")
    public ResponseEntity<Map<String, Object>> create(User user) {
        Map<String, Object> body = new HashMap<String, Object>();
        return us.create(user)
        .map(createdUser -> {
            body.put("msg", "Usuário cadastrado com sucesso");            
            body.put("user", createdUser);
            return ResponseEntity.ok(body);
        })
        .orElseGet(() -> {
            body.put("msg", "Falha ao cadastrar usuário");            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        });
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable(name = "id") Long iduser,
            @RequestBody User userRequest) {
        Map<String, Object> body = new HashMap<String, Object>();
        return us.update(iduser, userRequest)
        .map(savedUser ->{
            body.put("msg", "Usuário atualizado com sucesso");
            body.put("user", savedUser);
            return ResponseEntity.ok(body);
        }).orElseGet(() -> {
            body.put("msg", "Falha ao atualizar usuário");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        });
    }
}
