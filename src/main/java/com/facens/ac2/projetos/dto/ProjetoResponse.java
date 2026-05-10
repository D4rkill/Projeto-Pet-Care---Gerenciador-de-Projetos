package com.facens.ac2.projetos.dto;

import java.time.LocalDate;
import java.util.List;

public record ProjetoResponse(
        Long id,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<FuncionarioResponse> funcionarios
) {
}