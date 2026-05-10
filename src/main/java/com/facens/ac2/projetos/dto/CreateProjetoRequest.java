package com.facens.ac2.projetos.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateProjetoRequest(
        @NotBlank(message = "Descrição do projeto é obrigatória")
        @Size(min = 3, max = 160, message = "Descrição deve ter entre 3 e 160 caracteres")
        String descricao,

        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "Data final é obrigatória")
        LocalDate dataFim
) {
}