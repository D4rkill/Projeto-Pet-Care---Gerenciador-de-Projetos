package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.Especialidade;

public record VeterinarioResponse(
        Long id,
        String nome,
        String crmv,
        Especialidade especialidade
) {
}