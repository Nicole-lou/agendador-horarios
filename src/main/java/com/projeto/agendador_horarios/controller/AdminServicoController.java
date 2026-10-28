package com.projeto.agendador_horarios.controller;

import com.projeto.agendador_horarios.config.UsuarioLogado;
import com.projeto.agendador_horarios.dto.ServicoRequestDTO;
import com.projeto.agendador_horarios.dto.ServicoResponseDTO;
import com.projeto.agendador_horarios.services.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/servicos")
@RequiredArgsConstructor
public class AdminServicoController {

    private final ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar() {
        return ResponseEntity.ok(servicoService.listar(UsuarioLogado.uid()));
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> criar(@Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.status(201).body(servicoService.criar(dto, UsuarioLogado.uid()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.ok(servicoService.atualizar(id, dto, UsuarioLogado.uid()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        servicoService.remover(id, UsuarioLogado.uid());
        return ResponseEntity.noContent().build();
    }
}