package com.facens.ac2.petcare.dto;

import java.time.LocalDateTime;

public record ProntuarioResponse(
        Long id,
        LocalDateTime dataRegistro,
        String diagnostico,
        String tratamento,
        String observacoes,
        Long animalId,
        String animalNome,
        Long veterinarioId,
        String veterinarioNome,
        Long consultaId
) {
}