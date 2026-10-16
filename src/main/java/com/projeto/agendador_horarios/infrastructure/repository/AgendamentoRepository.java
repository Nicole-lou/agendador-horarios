package com.projeto.agendador_horarios.infrastructure.repository;

import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByServicoAndDataHoraAgendamentoBetween(
            String servico, LocalDateTime inicio, LocalDateTime fim);

    List<Agendamento> findByDataHoraAgendamentoBetween(
            LocalDateTime inicio, LocalDateTime fim);

    List<Agendamento> findByUsuarioUidOrderByDataHoraAgendamentoAsc(String usuarioUid);
}