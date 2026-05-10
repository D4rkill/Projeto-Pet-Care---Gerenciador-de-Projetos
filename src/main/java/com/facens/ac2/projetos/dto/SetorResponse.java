package com.facens.ac2.projetos.dto;

import java.util.List;

public record SetorResponse(
        Long id,
        String nome,
        List<FuncionarioResumoResponse> funcionarios
) {
}