package com.projeto.agendador_horarios.infrastructure.repository;

import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Verifica conflito de horário por serviço
    Optional<Agendamento> findByServicoAndDataHoraAgendamentoBetween(
            String servico, LocalDateTime inicio, LocalDateTime fim);

    // Busca todos os agendamentos de um dia
    List<Agendamento> findByDataHoraAgendamentoBetween(
            LocalDateTime inicio, LocalDateTime fim);

    // Busca agendamento específico para update/delete
    Optional<Agendamento> findByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento, String cliente);

    @Transactional
    void deleteByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento, String cliente);
}