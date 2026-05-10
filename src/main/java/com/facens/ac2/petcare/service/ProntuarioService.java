package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.CreateProntuarioRequest;
import com.facens.ac2.petcare.dto.ProntuarioResponse;
import com.facens.ac2.petcare.entity.*;
import com.facens.ac2.petcare.repository.AnimalRepository;
import com.facens.ac2.petcare.repository.ConsultaRepository;
import com.facens.ac2.petcare.repository.ProntuarioRepository;
import com.facens.ac2.petcare.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final ConsultaRepository consultaRepository;

    public ProntuarioService(
            ProntuarioRepository prontuarioRepository,
            AnimalRepository animalRepository,
            VeterinarioRepository veterinarioRepository,
            ConsultaRepository consultaRepository
    ) {
        this.prontuarioRepository = prontuarioRepository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.consultaRepository = consultaRepository;
    }

    public ProntuarioResponse registrar(CreateProntuarioRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado."));

        Consulta consulta = null;

        if (request.consultaId() != null) {
            consulta = consultaRepository.findById(request.consultaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));

            if (prontuarioRepository.existsByConsultaId(consulta.getId())) {
                throw new BusinessException("Já existe prontuário cadastrado para essa consulta.");
            }

            if (!consulta.getAnimal().getId().equals(animal.getId())) {
                throw new BusinessException("A consulta informada não pertence ao animal selecionado.");
            }

            if (!consulta.getVeterinario().getId().equals(veterinario.getId())) {
                throw new BusinessException("A consulta informada não pertence ao veterinário selecionado.");
            }

            if (consulta.getStatus() != StatusConsulta.CONCLUIDA) {
                throw new BusinessException("Só é possível registrar prontuário para consulta concluída.");
            }
        }

        Prontuario prontuario = Prontuario.builder()
                .dataRegistro(LocalDateTime.now())
                .diagnostico(request.diagnostico().trim())
                .tratamento(request.tratamento().trim())
                .observacoes(request.observacoes())
                .animal(animal)
                .veterinario(veterinario)
                .consulta(consulta)
                .build();

        return toResponse(prontuarioRepository.save(prontuario));
    }

    public List<ProntuarioResponse> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal não encontrado.");
        }

        return prontuarioRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProntuarioResponse> listarTodos() {
        return prontuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProntuarioResponse toResponse(Prontuario prontuario) {
        Long consultaId = prontuario.getConsulta() != null
                ? prontuario.getConsulta().getId()
                : null;

        return new ProntuarioResponse(
                prontuario.getId(),
                prontuario.getDataRegistro(),
                prontuario.getDiagnostico(),
                prontuario.getTratamento(),
                prontuario.getObservacoes(),
                prontuario.getAnimal().getId(),
                prontuario.getAnimal().getNome(),
                prontuario.getVeterinario().getId(),
                prontuario.getVeterinario().getNome(),
                consultaId
        );
    }
}