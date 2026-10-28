package com.projeto.agendador_horarios.infrastructure.repository;

import com.projeto.agendador_horarios.infrastructure.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    // "LojaId" faz o Spring entrar na relação: servico.loja.id
    List<Servico> findByLojaIdAndAtivoTrueOrderByNomeAsc(Long lojaId);

    Optional<Servico> findByIdAndLojaIdAndAtivoTrue(Long id, Long lojaId);

    boolean existsByLojaIdAndNomeIgnoreCaseAndAtivoTrue(Long lojaId, String nome);

    boolean existsByLojaIdAndNomeIgnoreCaseAndAtivoTrueAndIdNot(Long lojaId, String nome, Long id);
}