package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.CreateTutorRequest;
import com.facens.ac2.petcare.dto.TutorResponse;
import com.facens.ac2.petcare.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TutorResponse cadastrar(@Valid @RequestBody CreateTutorRequest request) {
        return tutorService.cadastrar(request);
    }

    @GetMapping
    public List<TutorResponse> listarTodos() {
        return tutorService.listarTodos();
    }

    @GetMapping("/{id}")
    public TutorResponse buscarPorId(@PathVariable Long id) {
        return tutorService.buscarPorId(id);
    }
}