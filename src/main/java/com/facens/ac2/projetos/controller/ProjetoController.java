package com.facens.ac2.projetos.controller;

import com.facens.ac2.projetos.dto.CreateProjetoRequest;
import com.facens.ac2.projetos.dto.ProjetoResponse;
import com.facens.ac2.projetos.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoResponse cadastrar(@Valid @RequestBody CreateProjetoRequest request) {
        return projetoService.cadastrar(request);
    }

    @GetMapping
    public List<ProjetoResponse> listarTodos() {
        return projetoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProjetoResponse buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorIdComFuncionarios(id);
    }

    @PostMapping("/{projetoId}/funcionarios/{funcionarioId}")
    public ProjetoResponse vincularFuncionario(
            @PathVariable Long projetoId,
            @PathVariable Long funcionarioId
    ) {
        return projetoService.vincularFuncionario(projetoId, funcionarioId);
    }

    @GetMapping("/periodo")
    public List<ProjetoResponse> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return projetoService.buscarPorPeriodo(inicio, fim);
    }
}