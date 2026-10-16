package com.projeto.agendador_horarios.controller;

import com.projeto.agendador_horarios.dto.AgendamentoRequestDTO;
import com.projeto.agendador_horarios.dto.AgendamentoResponseDTO;
import com.projeto.agendador_horarios.services.AgendamentoServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoServices agendamentoServices;

    private String uidLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvarAgendamento(
            @Valid @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.status(201)
                .body(agendamentoServices.salvarAgendamento(dto, uidLogado()));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentosDia(
            @RequestParam LocalDate data) {
        return ResponseEntity.ok(agendamentoServices.buscarAgendamentosDia(data));
    }

    @GetMapping("/meus")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarMeusAgendamentos() {
        return ResponseEntity.ok(agendamentoServices.buscarPorUsuario(uidLogado()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> alterarAgendamento(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoServices.alterarAgendamento(id, dto, uidLogado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoServices.deletarAgendamento(id, uidLogado());
        return ResponseEntity.noContent().build();
    }
}