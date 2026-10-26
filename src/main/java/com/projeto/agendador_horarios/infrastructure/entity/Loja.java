package com.projeto.agendador_horarios.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loja", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String slug;
    private String nicho;
    private String telefone;
    private String cor;

    // vira true quando o administrador conclui o último passo da configuração
    private boolean configurada = false;

    private LocalDateTime criadaEm = LocalDateTime.now();
}