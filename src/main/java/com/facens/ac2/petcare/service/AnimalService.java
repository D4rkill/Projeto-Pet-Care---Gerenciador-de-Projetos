package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.AnimalResponse;
import com.facens.ac2.petcare.dto.CreateAnimalRequest;
import com.facens.ac2.petcare.entity.Animal;
import com.facens.ac2.petcare.entity.Tutor;
import com.facens.ac2.petcare.repository.AnimalRepository;
import com.facens.ac2.petcare.repository.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    public AnimalService(
            AnimalRepository animalRepository,
            TutorRepository tutorRepository
    ) {
        this.animalRepository = animalRepository;
        this.tutorRepository = tutorRepository;
    }

    public AnimalResponse cadastrar(CreateAnimalRequest request) {
        Tutor tutor = tutorRepository.findById(request.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado."));

        Animal animal = Animal.builder()
                .nome(request.nome().trim())
                .especie(request.especie().trim())
                .raca(request.raca().trim())
                .idade(request.idade())
                .porte(request.porte())
                .tutor(tutor)
                .build();

        return toResponse(animalRepository.save(animal));
    }

    public Animal buscarEntidadePorId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));
    }

    public AnimalResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public List<AnimalResponse> listarTodos() {
        return animalRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AnimalResponse> listarPorTutor(Long tutorId) {
        if (!tutorRepository.existsById(tutorId)) {
            throw new ResourceNotFoundException("Tutor não encontrado.");
        }

        return animalRepository.findByTutorId(tutorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AnimalResponse toResponse(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getIdade(),
                animal.getPorte(),
                animal.getTutor().getId(),
                animal.getTutor().getNome()
        );
    }
}