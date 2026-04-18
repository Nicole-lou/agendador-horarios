package com.projeto.agendador_horarios.controller;

import com.projeto.agendador_horarios.infrastructure.entity.Agendamento;
import com.projeto.agendador_horarios.services.AgendamentoServices;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {
    private final AgendamentoServices agendamentoServices;

    @PostMapping
    public ResponseEntity<Agendamento> salvarAgendamento(@RequestBody Agendamento agendamento) {
        return ResponseEntity.accepted().body(agendamentoServices.salvarAgendamento(agendamento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento) {
        agendamentoServices.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> buscarAgendamentosDia(@RequestParam LocalDate data) {
        return ResponseEntity.ok().body((List<Agendamento>) agendamentoServices.buscarAgendamentosDia(data));
    }

    @PutMapping
    public ResponseEntity<Agendamento> alterarAgendamentos(@RequestBody Agendamento agendamento,
                                                           @RequestParam String cliente,
                                                           @RequestParam LocalDateTime dataHoraAgendamento) {
        return ResponseEntity.accepted().body(agendamentoServices.alterarAgendamento(agendamento,
                cliente, dataHoraAgendamento));
    }

}
