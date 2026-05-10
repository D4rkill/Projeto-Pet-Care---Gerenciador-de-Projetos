package com.facens.ac2.petcare.dto;

public record VacinaResponse(
        Long id,
        String nome,
        String fabricante,
        Integer intervaloReforcoMeses
) {
}