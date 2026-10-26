package com.projeto.agendador_horarios.infrastructure.repository;

import com.projeto.agendador_horarios.infrastructure.entity.Membro;
import com.projeto.agendador_horarios.infrastructure.entity.PapelMembro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro, Long> {

    Optional<Membro> findFirstByUsuarioUidAndPapel(String usuarioUid, PapelMembro papel);

    boolean existsByUsuarioUidAndPapel(String usuarioUid, PapelMembro papel);
}