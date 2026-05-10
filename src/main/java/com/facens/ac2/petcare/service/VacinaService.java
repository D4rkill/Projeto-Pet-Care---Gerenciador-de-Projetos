package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.CreateVacinaRequest;
import com.facens.ac2.petcare.dto.VacinaResponse;
import com.facens.ac2.petcare.entity.Vacina;
import com.facens.ac2.petcare.repository.VacinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacinaService {

    private final VacinaRepository vacinaRepository;

    public VacinaService(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    public VacinaResponse cadastrar(CreateVacinaRequest request) {
        String nomeNormalizado = request.nome().trim();

        if (vacinaRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new BusinessException("Já existe uma vacina cadastrada com esse nome.");
        }

        Vacina vacina = Vacina.builder()
                .nome(nomeNormalizado)
                .fabricante(request.fabricante().trim())
                .intervaloReforcoMeses(request.intervaloReforcoMeses())
                .build();

        return toResponse(vacinaRepository.save(vacina));
    }

    public Vacina buscarEntidadePorId(Long id) {
        return vacinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacina não encontrada."));
    }

    public List<VacinaResponse> listarTodas() {
        return vacinaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VacinaResponse toResponse(Vacina vacina) {
        return new VacinaResponse(
                vacina.getId(),
                vacina.getNome(),
                vacina.getFabricante(),
                vacina.getIntervaloReforcoMeses()
        );
    }
}