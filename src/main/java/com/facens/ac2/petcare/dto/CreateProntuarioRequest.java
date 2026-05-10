package com.facens.ac2.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProntuarioRequest(
        @NotNull(message = "Animal é obrigatório")
        Long animalId,

        @NotNull(message = "Veterinário é obrigatório")
        Long veterinarioId,

        Long consultaId,

        @NotBlank(message = "Diagnóstico é obrigatório")
        @Size(min = 3, max = 500, message = "Diagnóstico deve ter entre 3 e 500 caracteres")
        String diagnostico,

        @NotBlank(message = "Tratamento é obrigatório")
        @Size(min = 3, max = 500, message = "Tratamento deve ter entre 3 e 500 caracteres")
        String tratamento,

        @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
        String observacoes
) {
}