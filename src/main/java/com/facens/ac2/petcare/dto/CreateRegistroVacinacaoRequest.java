package com.facens.ac2.petcare.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateRegistroVacinacaoRequest(
        @NotNull(message = "Data de aplicação é obrigatória")
        @PastOrPresent(message = "A data de aplicação não pode estar no futuro")
        LocalDate dataAplicacao,

        @Size(max = 300, message = "Observação deve ter no máximo 300 caracteres")
        String observacao,

        @NotNull(message = "Animal é obrigatório")
        Long animalId,

        @NotNull(message = "Vacina é obrigatória")
        Long vacinaId,

        @NotNull(message = "Veterinário é obrigatório")
        Long veterinarioId
) {
}