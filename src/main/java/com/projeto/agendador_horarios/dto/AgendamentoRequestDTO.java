package com.projeto.agendador_horarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequestDTO {

    @NotBlank(message = "Serviço é obrigatório")
    private String servico;

    private String produto;

    @NotBlank(message = "Profissional é obrigatório")
    private String profissional;

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHoraAgendamento;

    @NotBlank(message = "Nome do cliente é obrigatório")
    private String cliente;

    @NotBlank(message = "Telefone do cliente é obrigatório")
    private String telefoneCliente;
}