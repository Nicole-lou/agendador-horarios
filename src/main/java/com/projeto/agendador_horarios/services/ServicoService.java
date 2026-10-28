package com.projeto.agendador_horarios.services;

import com.projeto.agendador_horarios.dto.ServicoRequestDTO;
import com.projeto.agendador_horarios.dto.ServicoResponseDTO;
import com.projeto.agendador_horarios.infrastructure.entity.Loja;
import com.projeto.agendador_horarios.infrastructure.entity.Servico;
import com.projeto.agendador_horarios.infrastructure.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final AcessoLojaService acessoLojaService;

    public List<ServicoResponseDTO> listar(String uid) {
        Loja loja = acessoLojaService.lojaDoAdmin(uid);
        return servicoRepository.findByLojaIdAndAtivoTrueOrderByNomeAsc(loja.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO dto, String uid) {
        Loja loja = acessoLojaService.lojaDoAdmin(uid);
        String nome = dto.getNome().trim();

        if (servicoRepository.existsByLojaIdAndNomeIgnoreCaseAndAtivoTrue(loja.getId(), nome)) {
            throw conflitoNome();
        }

        Servico servico = new Servico();
        servico.setLoja(loja);
        preencher(servico, dto, nome);
        return toDTO(servicoRepository.save(servico));
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto, String uid) {
        Loja loja = acessoLojaService.lojaDoAdmin(uid);
        Servico servico = buscarDaLoja(id, loja);
        String nome = dto.getNome().trim();

        if (servicoRepository.existsByLojaIdAndNomeIgnoreCaseAndAtivoTrueAndIdNot(
                loja.getId(), nome, id)) {
            throw conflitoNome();
        }

        preencher(servico, dto, nome);
        return toDTO(servicoRepository.save(servico));
    }

    @Transactional
    public void remover(Long id, String uid) {
        Loja loja = acessoLojaService.lojaDoAdmin(uid);
        Servico servico = buscarDaLoja(id, loja);
        servico.setAtivo(false);
        servicoRepository.save(servico);
    }

    // Busca pelo id E pela loja do administrador. Serviço de outra loja
    // não é encontrado, então vira 404 igual a um id que não existe.
    private Servico buscarDaLoja(Long id, Loja loja) {
        return servicoRepository.findByIdAndLojaIdAndAtivoTrue(id, loja.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Serviço não encontrado"));
    }

    private ResponseStatusException conflitoNome() {
        return new ResponseStatusException(
                HttpStatus.CONFLICT, "Você já tem um serviço com esse nome");
    }

    private void preencher(Servico servico, ServicoRequestDTO dto, String nome) {
        servico.setNome(nome);
        servico.setDuracaoMin(dto.getDuracaoMin());
        servico.setPreco(dto.getPreco());
    }

    private ServicoResponseDTO toDTO(Servico s) {
        return new ServicoResponseDTO(s.getId(), s.getNome(), s.getDuracaoMin(), s.getPreco());
    }
}