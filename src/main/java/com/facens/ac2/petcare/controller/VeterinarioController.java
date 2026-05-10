package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.CreateVeterinarioRequest;
import com.facens.ac2.petcare.dto.VeterinarioResponse;
import com.facens.ac2.petcare.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeterinarioResponse cadastrar(@Valid @RequestBody CreateVeterinarioRequest request) {
        return veterinarioService.cadastrar(request);
    }

    @GetMapping
    public List<VeterinarioResponse> listarTodos() {
        return veterinarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public VeterinarioResponse buscarPorId(@PathVariable Long id) {
        return veterinarioService.buscarPorId(id);
    }
}