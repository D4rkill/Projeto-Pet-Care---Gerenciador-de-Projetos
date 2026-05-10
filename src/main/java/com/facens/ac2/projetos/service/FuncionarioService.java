package com.facens.ac2.projetos.service;

import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.projetos.dto.CreateFuncionarioRequest;
import com.facens.ac2.projetos.dto.FuncionarioResponse;
import com.facens.ac2.projetos.entity.Funcionario;
import com.facens.ac2.projetos.entity.Setor;
import com.facens.ac2.projetos.repository.FuncionarioRepository;
import com.facens.ac2.projetos.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            SetorRepository setorRepository
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
    }

    public FuncionarioResponse cadastrar(CreateFuncionarioRequest request) {
        Setor setor = setorRepository.findById(request.setorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado."));

        Funcionario funcionario = Funcionario.builder()
                .nome(request.nome().trim())
                .setor(setor)
                .build();

        return toResponse(funcionarioRepository.save(funcionario));
    }

    public Funcionario buscarEntidadePorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado."));
    }

    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FuncionarioResponse toResponse(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getSetor().getId(),
                funcionario.getSetor().getNome()
        );
    }
}