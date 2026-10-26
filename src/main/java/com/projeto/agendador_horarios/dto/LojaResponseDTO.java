package com.projeto.agendador_horarios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LojaResponseDTO {
    private Long id;
    private String nome;
    private String slug;
    private String nicho;
    private String telefone;
    private String cor;
    private boolean configurada;
}