package com.jamir.hubvendas.model;

import java.math.BigDecimal;

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
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproduto;
    @Column(nullable = false)
    private String nome;
    @Column(precision = 10, scale = 2)
    private BigDecimal preco;
    @Column(nullable = false)
    private String descricao;
    @Column()
    private String img;
    @ManyToOne()
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
