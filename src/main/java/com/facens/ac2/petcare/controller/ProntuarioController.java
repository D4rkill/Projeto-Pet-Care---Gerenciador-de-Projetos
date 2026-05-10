package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.CreateProntuarioRequest;
import com.facens.ac2.petcare.dto.ProntuarioResponse;
import com.facens.ac2.petcare.service.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/prontuarios")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProntuarioResponse registrar(@Valid @RequestBody CreateProntuarioRequest request) {
        return prontuarioService.registrar(request);
    }

    @GetMapping
    public List<ProntuarioResponse> listarTodos() {
        return prontuarioService.listarTodos();
    }

    @GetMapping("/animal/{animalId}")
    public List<ProntuarioResponse> listarPorAnimal(@PathVariable Long animalId) {
        return prontuarioService.listarPorAnimal(animalId);
    }
}