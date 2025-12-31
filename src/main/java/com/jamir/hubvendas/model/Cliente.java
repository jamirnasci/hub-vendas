package com.jamir.hubvendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String telefone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(length = 8, nullable = false) private String cep;
    @Column(nullable = false) private String estado;
    @Column(nullable = false) private String cidade;
    @Column(nullable = false) private String bairro;
    @Column(nullable = false) private String rua;
    @Column(nullable = false) private int numApto;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
