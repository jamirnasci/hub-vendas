package com.jamir.hubvendas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcampanha;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private LocalDate dataInicio;
    private String dataFim;
    @Column(nullable = false)
    private int metaVendas;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumCampanha status;
    @Column(nullable = false)
    private String descricao;
    @Column()
    private String img;
}
