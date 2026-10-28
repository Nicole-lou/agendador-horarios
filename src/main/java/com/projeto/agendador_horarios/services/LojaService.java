package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.dto.LojaRequestDTO;
import com.projeto.agendador_horarios.dto.LojaResponseDTO;
import com.projeto.agendador_horarios.infrastructure.entity.Loja;
import com.projeto.agendador_horarios.infrastructure.entity.Membro;
import com.projeto.agendador_horarios.infrastructure.entity.PapelMembro;
import com.projeto.agendador_horarios.infrastructure.repository.LojaRepository;
import com.projeto.agendador_horarios.infrastructure.repository.MembroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final MembroRepository membroRepository;
    private final AcessoLojaService acessoLojaService;

    @Transactional
    public LojaResponseDTO criarLoja(LojaRequestDTO dto, String uid) {
        if (membroRepository.existsByUsuarioUidAndPapel(uid, PapelMembro.ADMIN)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Você já tem uma loja cadastrada");
        }
        validarSlugLivre(dto.getSlug());

        Loja loja = new Loja();
        preencher(loja, dto);
        salvar(loja);

        Membro membro = new Membro();
        membro.setUsuarioUid(uid);
        membro.setLoja(loja);
        membro.setPapel(PapelMembro.ADMIN);
        membroRepository.save(membro);

        return toDTO(loja);
    }

    public LojaResponseDTO buscarMinhaLoja(String uid) {
        return toDTO(acessoLojaService.lojaDoAdmin(uid));
    }

    @Transactional
    public LojaResponseDTO atualizarMinhaLoja(LojaRequestDTO dto, String uid) {
        Loja loja = acessoLojaService.lojaDoAdmin(uid);

        if (!loja.getSlug().equals(dto.getSlug())) {
            validarSlugLivre(dto.getSlug());
        }

        preencher(loja, dto);
        return toDTO(salvar(loja));
    }

    private void validarSlugLivre(String slug) {
        if (lojaRepository.existsBySlug(slug)) {
            throw conflitoSlug();
        }
    }

    // saveAndFlush envia ao banco na hora; se duas requisições disputarem o mesmo
    // slug, a restrição única do banco avisa aqui e viramos 409 em vez de 500
    private Loja salvar(Loja loja) {
        try {
            return lojaRepository.saveAndFlush(loja);
        } catch (DataIntegrityViolationException e) {
            throw conflitoSlug();
        }
    }

    private ResponseStatusException conflitoSlug() {
        return new ResponseStatusException(
                HttpStatus.CONFLICT, "Esse endereço de página já está em uso");
    }

    private void preencher(Loja loja, LojaRequestDTO dto) {
        loja.setNome(dto.getNome().trim());
        loja.setSlug(dto.getSlug());
        loja.setNicho(dto.getNicho().trim());
        loja.setTelefone(dto.getTelefone());
        loja.setCor(dto.getCor() != null ? dto.getCor() : "#D4AF37");
    }

    private LojaResponseDTO toDTO(Loja l) {
        return new LojaResponseDTO(
                l.getId(), l.getNome(), l.getSlug(), l.getNicho(),
                l.getTelefone(), l.getCor(), l.isConfigurada());
    }
}