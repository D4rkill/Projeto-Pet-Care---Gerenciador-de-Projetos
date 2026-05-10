package com.facens.ac2.projetos.dto;

public record FuncionarioResponse(
        Long id,
        String nome,
        Long setorId,
        String setorNome
) {
}