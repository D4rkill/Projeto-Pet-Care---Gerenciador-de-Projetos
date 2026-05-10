package com.facens.ac2.projetos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFuncionarioRequest(
        @NotBlank(message = "Nome do funcionário é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        String nome,

        @NotNull(message = "Setor é obrigatório")
        Long setorId
) {
}