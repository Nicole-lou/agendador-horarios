package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.infrastructure.entity.Loja;
import com.projeto.agendador_horarios.infrastructure.entity.PapelMembro;
import com.projeto.agendador_horarios.infrastructure.repository.MembroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// Ponto único para descobrir "qual é a loja deste administrador".
// Serviços, profissionais e horários vão usar este mesmo método.
@Service
@RequiredArgsConstructor
public class AcessoLojaService {

    private final MembroRepository membroRepository;

    public Loja lojaDoAdmin(String uid) {
        return membroRepository.findFirstByUsuarioUidAndPapel(uid, PapelMembro.ADMIN)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Você ainda não tem uma loja"))
                .getLoja();
    }
}