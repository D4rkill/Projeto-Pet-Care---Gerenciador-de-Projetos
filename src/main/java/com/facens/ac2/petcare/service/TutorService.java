package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.CreateTutorRequest;
import com.facens.ac2.petcare.dto.TutorResponse;
import com.facens.ac2.petcare.entity.Tutor;
import com.facens.ac2.petcare.repository.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public TutorResponse cadastrar(CreateTutorRequest request) {
        String emailNormalizado = request.email().trim().toLowerCase();

        if (tutorRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            throw new BusinessException("Já existe um tutor cadastrado com esse email.");
        }

        Tutor tutor = Tutor.builder()
                .nome(request.nome().trim())
                .email(emailNormalizado)
                .telefone(request.telefone().trim())
                .build();

        return toResponse(tutorRepository.save(tutor));
    }

    public Tutor buscarEntidadePorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado."));
    }

    public TutorResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public List<TutorResponse> listarTodos() {
        return tutorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TutorResponse toResponse(Tutor tutor) {
        return new TutorResponse(
                tutor.getId(),
                tutor.getNome(),
                tutor.getEmail(),
                tutor.getTelefone()
        );
    }
}