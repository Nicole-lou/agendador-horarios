package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.dto.AgendamentoRequestDTO;
import com.projeto.agendador_horarios.dto.AgendamentoResponseDTO;
import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import com.projeto.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoServices {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDTO salvarAgendamento(AgendamentoRequestDTO dto) {
        LocalDateTime horaFim = dto.getDataHoraAgendamento().plusHours(1);

        // lança exceção se horário JÁ está ocupado
        agendamentoRepository
                .findByServicoAndDataHoraAgendamentoBetween(
                        dto.getServico(), dto.getDataHoraAgendamento(), horaFim)
                .ifPresent(a -> {
                    throw new IllegalStateException("Horário já ocupado para este serviço");
                });

        Agendamento agendamento = toEntity(dto);
        return toDTO(agendamentoRepository.save(agendamento));
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {
        agendamentoRepository
                .findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(
                dataHoraAgendamento, cliente);
    }

    public List<AgendamentoResponseDTO> buscarAgendamentosDia(LocalDate data) {
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(23, 59, 59);

        return agendamentoRepository
                .findByDataHoraAgendamentoBetween(inicioDia, fimDia)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO alterarAgendamento(
            AgendamentoRequestDTO dto, String cliente, LocalDateTime dataHoraAgendamento) {

        Agendamento agenda = agendamentoRepository
                .findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agenda.setServico(dto.getServico());
        agenda.setProduto(dto.getProduto());
        agenda.setProfissional(dto.getProfissional());
        agenda.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        agenda.setCliente(dto.getCliente());
        agenda.setTelefoneCliente(dto.getTelefoneCliente());

        return toDTO(agendamentoRepository.save(agenda));
    }

    // Mappers manuais (simples, sem dependência extra)
    private Agendamento toEntity(AgendamentoRequestDTO dto) {
        Agendamento a = new Agendamento();
        a.setServico(dto.getServico());
        a.setProduto(dto.getProduto());
        a.setProfissional(dto.getProfissional());
        a.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        a.setCliente(dto.getCliente());
        a.setTelefoneCliente(dto.getTelefoneCliente());
        return a;
    }

    private AgendamentoResponseDTO toDTO(Agendamento a) {
        return new AgendamentoResponseDTO(
                a.getId(), a.getServico(), a.getProduto(),
                a.getProfissional(), a.getDataHoraAgendamento(),
                a.getCliente(), a.getTelefoneCliente(), a.getDataInsercao()
        );
    }
}