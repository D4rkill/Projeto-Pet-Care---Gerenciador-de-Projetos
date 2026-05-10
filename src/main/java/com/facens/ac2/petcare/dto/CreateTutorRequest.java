package com.facens.ac2.petcare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTutorRequest(
        @NotBlank(message = "Nome do tutor é obrigatório")
        @Size(min = 3, max = 120, message = "Nome deve ter entre 3 e 120 caracteres")
        String nome,

        @NotBlank(message = "Email do tutor é obrigatório")
        @Email(message = "Email inválido")
        @Size(max = 120, message = "Email deve ter no máximo 120 caracteres")
        String email,

        @NotBlank(message = "Telefone do tutor é obrigatório")
        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String telefone
) {
}