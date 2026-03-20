package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import com.projeto.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgendamentoServices {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);

     Agendamento agendados =  agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(),
             horaAgendamento, horaFim);

     if (Objects.nonNull(agendados)){
         agendamentoRepository.save(agendamento);
     }
     return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {

    agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public Agendamento buscarAgendamentosDia(LocalDate data) {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23 , 59, 58);

        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }

  public Agendamento alterarAgendamento(Agendamento agendamento, LocalDateTime dataHoraAgendamento, String cliente) {
    Agendamento agenda = agendamentoRepository findByByDataHoraAgendamentoAndCliente (dataHoraAgendamento, cliente);
  }

}
