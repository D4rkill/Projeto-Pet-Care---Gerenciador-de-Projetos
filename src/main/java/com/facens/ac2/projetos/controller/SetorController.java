package com.facens.ac2.projetos.controller;

import com.facens.ac2.projetos.dto.CreateSetorRequest;
import com.facens.ac2.projetos.dto.SetorResponse;
import com.facens.ac2.projetos.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetorResponse cadastrar(@Valid @RequestBody CreateSetorRequest request) {
        return setorService.cadastrar(request);
    }

    @GetMapping("/{id}")
    public SetorResponse buscarPorId(@PathVariable Long id) {
        return setorService.buscarPorId(id);
    }

    @GetMapping
    public List<SetorResponse> listarTodos() {
        return setorService.listarTodosComFuncionarios();
    }
}