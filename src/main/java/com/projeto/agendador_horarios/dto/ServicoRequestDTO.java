package com.projeto.agendador_horarios.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServicoRequestDTO {

    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(max = 80)
    private String nome;

    @NotNull(message = "Duração é obrigatória")
    @Min(value = 5, message = "A duração mínima é de 5 minutos")
    @Max(value = 480, message = "A duração máxima é de 480 minutos")
    private Integer duracaoMin;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço não pode ser negativo")
    @Digits(integer = 6, fraction = 2, message = "Preço inválido")
    private BigDecimal preco;
}