package com.facens.ac2.petcare.dto;

import java.time.LocalDate;

public record RegistroVacinacaoResponse(
        Long id,
        LocalDate dataAplicacao,
        LocalDate dataProximoReforco,
        String observacao,
        Long animalId,
        String animalNome,
        Long vacinaId,
        String vacinaNome,
        Long veterinarioId,
        String veterinarioNome
) {
}