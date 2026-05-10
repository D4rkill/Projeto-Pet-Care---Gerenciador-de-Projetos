package com.facens.ac2.petcare.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.petcare.dto.CreateVeterinarioRequest;
import com.facens.ac2.petcare.dto.VeterinarioResponse;
import com.facens.ac2.petcare.entity.Veterinario;
import com.facens.ac2.petcare.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public VeterinarioResponse cadastrar(CreateVeterinarioRequest request) {
        String crmvNormalizado = request.crmv().trim().toUpperCase();

        if (veterinarioRepository.existsByCrmvIgnoreCase(crmvNormalizado)) {
            throw new BusinessException("Já existe um veterinário cadastrado com esse CRMV.");
        }

        Veterinario veterinario = Veterinario.builder()
                .nome(request.nome().trim())
                .crmv(crmvNormalizado)
                .especialidade(request.especialidade())
                .build();

        return toResponse(veterinarioRepository.save(veterinario));
    }

    public Veterinario buscarEntidadePorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado."));
    }

    public VeterinarioResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public List<VeterinarioResponse> listarTodos() {
        return veterinarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VeterinarioResponse toResponse(Veterinario veterinario) {
        return new VeterinarioResponse(
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getCrmv(),
                veterinario.getEspecialidade()
        );
    }
}