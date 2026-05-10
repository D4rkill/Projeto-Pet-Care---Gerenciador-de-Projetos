package com.facens.ac2.petcare.dto;

import com.facens.ac2.petcare.entity.PorteAnimal;

public record AnimalResponse(
        Long id,
        String nome,
        String especie,
        String raca,
        Integer idade,
        PorteAnimal porte,
        Long tutorId,
        String tutorNome
) {
}