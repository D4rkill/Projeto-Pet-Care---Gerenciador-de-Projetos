package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.CreateRegistroVacinacaoRequest;
import com.facens.ac2.petcare.dto.RegistroVacinacaoResponse;
import com.facens.ac2.petcare.service.RegistroVacinacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/vacinacoes")
public class RegistroVacinacaoController {

    private final RegistroVacinacaoService registroVacinacaoService;

    public RegistroVacinacaoController(RegistroVacinacaoService registroVacinacaoService) {
        this.registroVacinacaoService = registroVacinacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroVacinacaoResponse registrar(@Valid @RequestBody CreateRegistroVacinacaoRequest request) {
        return registroVacinacaoService.registrar(request);
    }

    @GetMapping
    public List<RegistroVacinacaoResponse> listarTodas() {
        return registroVacinacaoService.listarTodos();
    }

    @GetMapping("/animal/{animalId}")
    public List<RegistroVacinacaoResponse> listarPorAnimal(@PathVariable Long animalId) {
        return registroVacinacaoService.listarPorAnimal(animalId);
    }
}