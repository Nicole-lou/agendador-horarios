package com.projeto.agendador_horarios.controller;

import com.projeto.agendador_horarios.dto.AgendamentoRequestDTO;
import com.projeto.agendador_horarios.dto.AgendamentoResponseDTO;
import com.projeto.agendador_horarios.services.AgendamentoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoServices agendamentoServices;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvarAgendamento(
            @Validated @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.status(201).body(agendamentoServices.salvarAgendamento(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(
            @RequestParam String cliente,
            @RequestParam LocalDateTime dataHoraAgendamento) {
        agendamentoServices.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentosDia(
            @RequestParam LocalDate data) {
        return ResponseEntity.ok(agendamentoServices.buscarAgendamentosDia(data));
    }

    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> alterarAgendamento(
            @Validated @RequestBody AgendamentoRequestDTO dto,
            @RequestParam String cliente,
            @RequestParam LocalDateTime dataHoraAgendamento) {
        return ResponseEntity.accepted()
                .body(agendamentoServices.alterarAgendamento(dto, cliente, dataHoraAgendamento));
    }
}