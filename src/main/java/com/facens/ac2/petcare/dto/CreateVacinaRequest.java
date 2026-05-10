package com.facens.ac2.petcare.dto;

import jakarta.validation.constraints.*;

public record CreateVacinaRequest(
        @NotBlank(message = "Nome da vacina é obrigatório")
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String nome,

        @NotBlank(message = "Fabricante é obrigatório")
        @Size(min = 2, max = 120, message = "Fabricante deve ter entre 2 e 120 caracteres")
        String fabricante,

        @NotNull(message = "Intervalo de reforço é obrigatório")
        @Min(value = 1, message = "Intervalo deve ser de pelo menos 1 mês")
        @Max(value = 120, message = "Intervalo muito alto")
        Integer intervaloReforcoMeses
) {
}