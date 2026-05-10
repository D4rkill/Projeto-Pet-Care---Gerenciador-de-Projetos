package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.CreateVacinaRequest;
import com.facens.ac2.petcare.dto.VacinaResponse;
import com.facens.ac2.petcare.service.VacinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/vacinas")
public class VacinaController {

    private final VacinaService vacinaService;

    public VacinaController(VacinaService vacinaService) {
        this.vacinaService = vacinaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VacinaResponse cadastrar(@Valid @RequestBody CreateVacinaRequest request) {
        return vacinaService.cadastrar(request);
    }

    @GetMapping
    public List<VacinaResponse> listarTodas() {
        return vacinaService.listarTodas();
    }
}