package com.facens.ac2.projetos.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.projetos.dto.CreateSetorRequest;
import com.facens.ac2.projetos.dto.FuncionarioResumoResponse;
import com.facens.ac2.projetos.dto.SetorResponse;
import com.facens.ac2.projetos.entity.Setor;
import com.facens.ac2.projetos.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public SetorResponse cadastrar(CreateSetorRequest request) {
        String nomeNormalizado = request.nome().trim();

        if (setorRepository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new BusinessException("Já existe um setor cadastrado com esse nome.");
        }

        Setor setor = Setor.builder()
                .nome(nomeNormalizado)
                .build();

        return toResponse(setorRepository.save(setor));
    }

    public SetorResponse buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado."));

        return toResponse(setor);
    }

    public List<SetorResponse> listarTodosComFuncionarios() {
        return setorRepository.listarSetoresComFuncionarios()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SetorResponse toResponse(Setor setor) {
        List<FuncionarioResumoResponse> funcionarios = setor.getFuncionarios()
                .stream()
                .map(funcionario -> new FuncionarioResumoResponse(
                        funcionario.getId(),
                        funcionario.getNome()
                ))
                .toList();

        return new SetorResponse(
                setor.getId(),
                setor.getNome(),
                funcionarios
        );
    }
}