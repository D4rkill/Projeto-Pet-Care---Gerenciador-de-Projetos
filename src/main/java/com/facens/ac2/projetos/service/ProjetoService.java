package com.facens.ac2.projetos.service;

import com.facens.ac2.exception.BusinessException;
import com.facens.ac2.exception.ResourceNotFoundException;
import com.facens.ac2.projetos.dto.CreateProjetoRequest;
import com.facens.ac2.projetos.dto.FuncionarioResponse;
import com.facens.ac2.projetos.dto.ProjetoResponse;
import com.facens.ac2.projetos.entity.Funcionario;
import com.facens.ac2.projetos.entity.Projeto;
import com.facens.ac2.projetos.repository.FuncionarioRepository;
import com.facens.ac2.projetos.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoService(
            ProjetoRepository projetoRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public ProjetoResponse cadastrar(CreateProjetoRequest request) {
        validarDatas(request.dataInicio(), request.dataFim());

        Projeto projeto = Projeto.builder()
                .descricao(request.descricao().trim())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .build();

        return toResponse(projetoRepository.save(projeto));
    }

    @Transactional(readOnly = true)
    public ProjetoResponse buscarPorIdComFuncionarios(Long id) {
        Projeto projeto = projetoRepository.buscarProjetoComFuncionariosPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado."));

        return toResponse(projeto);
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponse> listarTodos() {
        return projetoRepository.listarTodosComFuncionarios()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProjetoResponse vincularFuncionario(Long projetoId, Long funcionarioId) {
        Projeto projeto = projetoRepository.buscarProjetoComFuncionariosPorId(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado."));

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado."));

        boolean jaVinculado = projeto.getFuncionarios()
                .stream()
                .anyMatch(f -> f.getId().equals(funcionarioId));

        if (jaVinculado) {
            throw new BusinessException("Funcionário já está vinculado a este projeto.");
        }

        projeto.getFuncionarios().add(funcionario);

        return toResponse(projetoRepository.save(projeto));
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarDatas(inicio, fim);

        return projetoRepository.buscarProjetosPorPeriodo(inicio, fim)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponse> buscarPorFuncionario(Long funcionarioId) {
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new ResourceNotFoundException("Funcionário não encontrado.");
        }

        return projetoRepository.buscarProjetosPorFuncionario(funcionarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new BusinessException("A data de início não pode ser posterior à data final.");
        }
    }

    private ProjetoResponse toResponse(Projeto projeto) {
        List<FuncionarioResponse> funcionarios = projeto.getFuncionarios()
                .stream()
                .map(funcionario -> new FuncionarioResponse(
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getSetor().getId(),
                        funcionario.getSetor().getNome()
                ))
                .toList();

        return new ProjetoResponse(
                projeto.getId(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                funcionarios
        );
    }
}