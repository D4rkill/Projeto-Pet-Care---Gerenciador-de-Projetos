package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.Especialidade;
import com.facens.ac2.petcare.entity.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        LocalDateTime dataHora,
        Especialidade especialidadeConsulta,
        StatusConsulta status,
        String observacao,
        Long animalId,
        String animalNome,
        Long tutorId,
        String tutorNome,
        Long veterinarioId,
        String veterinarioNome,
        Especialidade especialidadeVeterinario
) {
}