package com.facens.ac2.projetos.controller;

import com.facens.ac2.projetos.dto.CreateFuncionarioRequest;
import com.facens.ac2.projetos.dto.FuncionarioResponse;
import com.facens.ac2.projetos.dto.ProjetoResponse;
import com.facens.ac2.projetos.service.FuncionarioService;
import com.facens.ac2.projetos.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final ProjetoService projetoService;

    public FuncionarioController(
            FuncionarioService funcionarioService,
            ProjetoService projetoService
    ) {
        this.funcionarioService = funcionarioService;
        this.projetoService = projetoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponse cadastrar(@Valid @RequestBody CreateFuncionarioRequest request) {
        return funcionarioService.cadastrar(request);
    }

    @GetMapping
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @GetMapping("/{id}/projetos")
    public List<ProjetoResponse> buscarProjetosPorFuncionario(@PathVariable Long id) {
        return projetoService.buscarPorFuncionario(id);
    }
}