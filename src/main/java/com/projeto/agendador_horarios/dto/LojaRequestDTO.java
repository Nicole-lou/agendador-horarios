package com.projeto.agendador_horarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaRequestDTO {

    @NotBlank(message = "Nome da loja é obrigatório")
    @Size(max = 80)
    private String nome;

    @NotBlank(message = "Endereço da página é obrigatório")
    @Size(min = 3, max = 60)
    @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$",
            message = "Use só letras minúsculas, números e hífen")
    private String slug;

    @NotBlank(message = "Nicho é obrigatório")
    @Size(max = 40)
    private String nicho;

    @Size(max = 20)
    private String telefone;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Cor inválida")
    private String cor;
}