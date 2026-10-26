package com.projeto.agendador_horarios.controller;

import com.projeto.agendador_horarios.config.UsuarioLogado;
import com.projeto.agendador_horarios.dto.LojaRequestDTO;
import com.projeto.agendador_horarios.dto.LojaResponseDTO;
import com.projeto.agendador_horarios.services.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/loja")
@RequiredArgsConstructor
public class AdminLojaController {

    private final LojaService lojaService;

    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@Valid @RequestBody LojaRequestDTO dto) {
        return ResponseEntity.status(201).body(lojaService.criarLoja(dto, UsuarioLogado.uid()));
    }

    @GetMapping("/minha")
    public ResponseEntity<LojaResponseDTO> minha() {
        return ResponseEntity.ok(lojaService.buscarMinhaLoja(UsuarioLogado.uid()));
    }

    @PutMapping("/minha")
    public ResponseEntity<LojaResponseDTO> atualizar(@Valid @RequestBody LojaRequestDTO dto) {
        return ResponseEntity.ok(lojaService.atualizarMinhaLoja(dto, UsuarioLogado.uid()));
    }
}