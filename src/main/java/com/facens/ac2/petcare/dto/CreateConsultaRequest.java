package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.Especialidade;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateConsultaRequest(
        @NotNull(message = "Data e hora da consulta são obrigatórias")
        @FutureOrPresent(message = "A consulta não pode ser agendada no passado")
        LocalDateTime dataHora,

        @NotNull(message = "Especialidade da consulta é obrigatória")
        Especialidade especialidadeConsulta,

        @Size(max = 300, message = "Observação deve ter no máximo 300 caracteres")
        String observacao,

        @NotNull(message = "Animal é obrigatório")
        Long animalId,

        @NotNull(message = "Veterinário é obrigatório")
        Long veterinarioId
) {
}