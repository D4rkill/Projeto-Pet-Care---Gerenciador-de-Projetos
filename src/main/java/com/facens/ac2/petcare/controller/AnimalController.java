package com.facens.ac2.petcare.controller;

import com.facens.ac2.petcare.dto.AnimalResponse;
import com.facens.ac2.petcare.dto.CreateAnimalRequest;
import com.facens.ac2.petcare.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petcare/animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse cadastrar(@Valid @RequestBody CreateAnimalRequest request) {
        return animalService.cadastrar(request);
    }

    @GetMapping
    public List<AnimalResponse> listarTodos() {
        return animalService.listarTodos();
    }

    @GetMapping("/{id}")
    public AnimalResponse buscarPorId(@PathVariable Long id) {
        return animalService.buscarPorId(id);
    }

    @GetMapping("/tutor/{tutorId}")
    public List<AnimalResponse> listarPorTutor(@PathVariable Long tutorId) {
        return animalService.listarPorTutor(tutorId);
    }
}