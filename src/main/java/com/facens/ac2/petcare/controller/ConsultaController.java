package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.ConsultaResponse;
import com.facens.ac2.petcare.dto.CreateConsultaRequest;
import com.facens.ac2.petcare.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendar(@Valid @RequestBody CreateConsultaRequest request) {
        return consultaService.agendar(request);
    }

    @GetMapping
    public List<ConsultaResponse> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ConsultaResponse buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id);
    }

    @GetMapping("/animal/{animalId}")
    public List<ConsultaResponse> listarPorAnimal(@PathVariable Long animalId) {
        return consultaService.listarPorAnimal(animalId);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public List<ConsultaResponse> listarPorVeterinario(@PathVariable Long veterinarioId) {
        return consultaService.listarPorVeterinario(veterinarioId);
    }

    @PatchMapping("/{id}/concluir")
    public ConsultaResponse concluir(@PathVariable Long id) {
        return consultaService.concluirConsulta(id);
    }

    @PatchMapping("/{id}/cancelar")
    public ConsultaResponse cancelar(@PathVariable Long id) {
        return consultaService.cancelarConsulta(id);
    }
}