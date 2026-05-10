package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateVeterinarioRequest(
        @NotBlank(message = "Nome do veterinário é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        String nome,

        @NotBlank(message = "CRMV é obrigatório")
        @Size(min = 4, max = 30, message = "CRMV deve ter entre 4 e 30 caracteres")
        String crmv,

        @NotNull(message = "Especialidade é obrigatória")
        Especialidade especialidade
) {
}