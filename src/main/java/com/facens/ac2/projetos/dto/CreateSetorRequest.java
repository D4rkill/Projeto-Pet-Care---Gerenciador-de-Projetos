package com.facens.ac2.projetos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSetorRequest(
        @NotBlank(message = "Nome do setor é obrigatório")
        @Size(min = 2, max = 100, message = "Nome do setor deve ter entre 2 e 100 caracteres")
        String nome
) {
}