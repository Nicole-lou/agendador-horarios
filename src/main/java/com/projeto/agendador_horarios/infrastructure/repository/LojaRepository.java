package com.projeto.agendador_horarios.infrastructure.repository;

import com.projeto.agendador_horarios.infrastructure.entity.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Long> {

    boolean existsBySlug(String slug);
}