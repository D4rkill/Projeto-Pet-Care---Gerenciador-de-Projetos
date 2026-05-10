package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.CreateRegistroVacinacaoRequest;
import com.facens.ac2.petcare.dto.RegistroVacinacaoResponse;
import com.facens.ac2.petcare.entity.*;
import com.facens.ac2.petcare.repository.AnimalRepository;
import com.facens.ac2.petcare.repository.RegistroVacinacaoRepository;
import com.facens.ac2.petcare.repository.VacinaRepository;
import com.facens.ac2.petcare.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegistroVacinacaoService {

    private final RegistroVacinacaoRepository registroVacinacaoRepository;
    private final AnimalRepository animalRepository;
    private final VacinaRepository vacinaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public RegistroVacinacaoService(
            RegistroVacinacaoRepository registroVacinacaoRepository,
            AnimalRepository animalRepository,
            VacinaRepository vacinaRepository,
            VeterinarioRepository veterinarioRepository
    ) {
        this.registroVacinacaoRepository = registroVacinacaoRepository;
        this.animalRepository = animalRepository;
        this.vacinaRepository = vacinaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public RegistroVacinacaoResponse registrar(CreateRegistroVacinacaoRequest request) {
        Animal animal = animalRepository.findById(request.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado."));

        Vacina vacina = vacinaRepository.findById(request.vacinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada."));

        Veterinario veterinario = veterinarioRepository.findById(request.veterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado."));

        if (veterinario.getEspecialidade() != Especialidade.VACINACAO
                && veterinario.getEspecialidade() != Especialidade.CLINICA_GERAL) {
            throw new BusinessException("Apenas veterinário de vacinação ou clínica geral pode registrar vacinação.");
        }

        LocalDate dataProximoReforco = request.dataAplicacao()
                .plusMonths(vacina.getIntervaloReforcoMeses());

        RegistroVacinacao registro = RegistroVacinacao.builder()
                .dataAplicacao(request.dataAplicacao())
                .dataProximoReforco(dataProximoReforco)
                .observacao(request.observacao())
                .animal(animal)
                .vacina(vacina)
                .veterinario(veterinario)
                .build();

        return toResponse(registroVacinacaoRepository.save(registro));
    }

    public List<RegistroVacinacaoResponse> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal não encontrado.");
        }

        return registroVacinacaoRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RegistroVacinacaoResponse> listarTodos() {
        return registroVacinacaoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RegistroVacinacaoResponse toResponse(RegistroVacinacao registro) {
        return new RegistroVacinacaoResponse(
                registro.getId(),
                registro.getDataAplicacao(),
                registro.getDataProximoReforco(),
                registro.getObservacao(),
                registro.getAnimal().getId(),
                registro.getAnimal().getNome(),
                registro.getVacina().getId(),
                registro.getVacina().getNome(),
                registro.getVeterinario().getId(),
                registro.getVeterinario().getNome()
        );
    }
}