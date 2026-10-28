package com.projeto.agendador_horarios.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // cada serviço pertence a uma loja
    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    private String nome;

    private int duracaoMin;

    // dinheiro sempre em BigDecimal, nunca em double
    @Column(precision = 8, scale = 2)
    private BigDecimal preco;

    // "remover" só marca como inativo, para não quebrar o histórico de agendamentos
    private boolean ativo = true;
}