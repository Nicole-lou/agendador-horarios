package com.projeto.agendador_horarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ServicoResponseDTO {
    private Long id;
    private String nome;
    private int duracaoMin;
    private BigDecimal preco;
}