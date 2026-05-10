package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.PorteAnimal;
import jakarta.validation.constraints.*;

public record CreateAnimalRequest(
        @NotBlank(message = "Nome do animal é obrigatório")
        @Size(min = 2, max = 120, message = "Nome deve ter entre 2 e 120 caracteres")
        String nome,

        @NotBlank(message = "Espécie é obrigatória")
        @Size(min = 3, max = 80, message = "Espécie deve ter entre 3 e 80 caracteres")
        String especie,

        @NotBlank(message = "Raça é obrigatória")
        @Size(min = 3, max = 80, message = "Raça deve ter entre 3 e 80 caracteres")
        String raca,

        @NotNull(message = "Idade é obrigatória")
        @Min(value = 0, message = "Idade não pode ser negativa")
        @Max(value = 50, message = "Idade inválida")
        Integer idade,

        @NotNull(message = "Porte é obrigatório")
        PorteAnimal porte,

        @NotNull(message = "Tutor é obrigatório")
        Long tutorId
) {
}