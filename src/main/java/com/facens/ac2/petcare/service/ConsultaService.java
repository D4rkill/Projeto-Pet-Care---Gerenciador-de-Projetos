package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.ConsultaResponse;
import com.facens.ac2.petcare.dto.CreateConsultaRequest;
import com.facens.ac2.petcare.entity.*;
import com.facens.ac2.petcare.repository.AnimalRepository;
import com.facens.ac2.petcare.repository.ConsultaRepository;
import com.facens.ac2.petcare.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            AnimalRepository animalRepository,
            VeterinarioRepository veterinarioRepository
    ) {
        this.consultaRepository = consultaRepository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public ConsultaResponse agendar(CreateConsultaRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado."));

        validarEspecialidade(veterinario, request.especialidadeConsulta());
        validarConflitoAgenda(veterinario.getId(), request);

        Consulta consulta = Consulta.builder()
                .dataHora(request.dataHora())
                .especialidadeConsulta(request.especialidadeConsulta())
                .status(StatusConsulta.AGENDADA)
                .observacao(request.observacao())
                .animal(animal)
                .veterinario(veterinario)
                .build();

        return toResponse(consultaRepository.save(consulta));
    }

    public Consulta buscarEntidadePorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
    }

    public ConsultaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public List<ConsultaResponse> listarTodas() {
        return consultaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ConsultaResponse> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal não encontrado.");
        }

        return consultaRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ConsultaResponse> listarPorVeterinario(Long veterinarioId) {
        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new ResourceNotFoundException("Veterinário não encontrado.");
        }

        return consultaRepository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ConsultaResponse concluirConsulta(Long id) {
        Consulta consulta = buscarEntidadePorId(id);

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new BusinessException("Não é possível concluir uma consulta cancelada.");
        }

        if (consulta.getStatus() == StatusConsulta.CONCLUIDA) {
            throw new BusinessException("Consulta já está concluída.");
        }

        consulta.setStatus(StatusConsulta.CONCLUIDA);

        return toResponse(consultaRepository.save(consulta));
    }

    public ConsultaResponse cancelarConsulta(Long id) {
        Consulta consulta = buscarEntidadePorId(id);

        if (consulta.getStatus() == StatusConsulta.CONCLUIDA) {
            throw new BusinessException("Não é possível cancelar uma consulta concluída.");
        }

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new BusinessException("Consulta já está cancelada.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);

        return toResponse(consultaRepository.save(consulta));
    }

    private void validarEspecialidade(Veterinario veterinario, Especialidade especialidadeConsulta) {
        if (!veterinario.getEspecialidade().equals(especialidadeConsulta)) {
            throw new BusinessException("Veterinário não atende a especialidade solicitada.");
        }
    }

    private void validarConflitoAgenda(Long veterinarioId, CreateConsultaRequest request) {
        boolean existeConflito = consultaRepository.existsByVeterinarioIdAndDataHoraAndStatusNot(
                veterinarioId,
                request.dataHora(),
                StatusConsulta.CANCELADA
        );

        if (existeConflito) {
            throw new BusinessException("Veterinário já possui consulta nesse horário.");
        }
    }

    public ConsultaResponse toResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getDataHora(),
                consulta.getEspecialidadeConsulta(),
                consulta.getStatus(),
                consulta.getObservacao(),
                consulta.getAnimal().getId(),
                consulta.getAnimal().getNome(),
                consulta.getAnimal().getTutor().getId(),
                consulta.getAnimal().getTutor().getNome(),
                consulta.getVeterinario().getId(),
                consulta.getVeterinario().getNome(),
                consulta.getVeterinario().getEspecialidade()
        );
    }
}