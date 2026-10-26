package com.projeto.agendador_horarios.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membro",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_uid", "loja_id"}))
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // UID do Firebase da pessoa
    private String usuarioUid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @Enumerated(EnumType.STRING)
    private PapelMembro papel;
}