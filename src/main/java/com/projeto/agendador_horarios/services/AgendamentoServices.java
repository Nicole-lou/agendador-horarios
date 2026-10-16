package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.dto.AgendamentoRequestDTO;
import com.projeto.agendador_horarios.dto.AgendamentoResponseDTO;
import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import com.projeto.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoServices {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDTO salvarAgendamento(AgendamentoRequestDTO dto, String usuarioUid) {
        validarDataFutura(dto.getDataHoraAgendamento());
        validarHorarioLivre(dto, null);

        Agendamento agendamento = toEntity(dto, usuarioUid);
        return toDTO(agendamentoRepository.save(agendamento));
    }

    public void deletarAgendamento(Long id, String usuarioUid) {
        Agendamento agendamento = buscarDoUsuario(id, usuarioUid);
        agendamentoRepository.delete(agendamento);
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

    public List<AgendamentoResponseDTO> buscarPorUsuario(String usuarioUid) {
        return agendamentoRepository
                .findByUsuarioUidOrderByDataHoraAgendamentoAsc(usuarioUid)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO alterarAgendamento(
            Long id, AgendamentoRequestDTO dto, String usuarioUid) {

        Agendamento agenda = buscarDoUsuario(id, usuarioUid);

        validarDataFutura(dto.getDataHoraAgendamento());
        validarHorarioLivre(dto, id);

        agenda.setServico(dto.getServico());
        agenda.setProduto(dto.getProduto());
        agenda.setProfissional(dto.getProfissional());
        agenda.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        agenda.setCliente(dto.getCliente());
        agenda.setTelefoneCliente(dto.getTelefoneCliente());

        return toDTO(agendamentoRepository.save(agenda));
    }

    // Busca pelo id e garante que o agendamento pertence ao usuário logado
    private Agendamento buscarDoUsuario(Long id, String usuarioUid) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (agendamento.getUsuarioUid() == null
                || !agendamento.getUsuarioUid().equals(usuarioUid)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este agendamento");
        }
        return agendamento;
    }

    private void validarDataFutura(LocalDateTime dataHora) {
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Não é possível agendar em data/hora que já passou");
        }
    }

    // idIgnorado: no PUT, o próprio agendamento não conta como conflito
    private void validarHorarioLivre(AgendamentoRequestDTO dto, Long idIgnorado) {
        LocalDateTime horaFim = dto.getDataHoraAgendamento().plusHours(1);

        agendamentoRepository
                .findByServicoAndDataHoraAgendamentoBetween(
                        dto.getServico(), dto.getDataHoraAgendamento(), horaFim)
                .filter(a -> idIgnorado == null || !a.getId().equals(idIgnorado))
                .ifPresent(a -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "Horário já ocupado para este serviço");
                });
    }

    private Agendamento toEntity(AgendamentoRequestDTO dto, String usuarioUid) {
        Agendamento a = new Agendamento();
        a.setServico(dto.getServico());
        a.setProduto(dto.getProduto());
        a.setProfissional(dto.getProfissional());
        a.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        a.setCliente(dto.getCliente());
        a.setTelefoneCliente(dto.getTelefoneCliente());
        a.setUsuarioUid(usuarioUid);
        return a;
    }

    private AgendamentoResponseDTO toDTO(Agendamento a) {
        return new AgendamentoResponseDTO(
                a.getId(), a.getServico(), a.getProduto(),
                a.getProfissional(), a.getDataHoraAgendamento(),
                a.getCliente(), a.getTelefoneCliente(), a.getUsuarioUid(),
                a.getDataInsercao()
        );
    }
}