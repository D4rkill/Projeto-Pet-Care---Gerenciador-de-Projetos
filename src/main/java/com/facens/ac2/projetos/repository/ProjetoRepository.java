package com.facens.ac2.projetos.repository;

import com.facens.ac2.projetos.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("""
           SELECT DISTINCT p
           FROM Projeto p
           LEFT JOIN FETCH p.funcionarios f
           LEFT JOIN FETCH f.setor
           """)
    List<Projeto> listarTodosComFuncionarios();

    @Query("""
           SELECT DISTINCT p
           FROM Projeto p
           LEFT JOIN FETCH p.funcionarios f
           LEFT JOIN FETCH f.setor
           WHERE p.id = :id
           """)
    Optional<Projeto> buscarProjetoComFuncionariosPorId(@Param("id") Long id);

    @Query("""
           SELECT DISTINCT p
           FROM Projeto p
           LEFT JOIN FETCH p.funcionarios f
           LEFT JOIN FETCH f.setor
           WHERE p.dataInicio >= :inicio
           AND p.dataFim <= :fim
           """)
    List<Projeto> buscarProjetosPorPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
           SELECT DISTINCT p
           FROM Projeto p
           JOIN p.funcionarios f
           LEFT JOIN FETCH p.funcionarios funcionarios
           LEFT JOIN FETCH funcionarios.setor
           WHERE f.id = :funcionarioId
           """)
    List<Projeto> buscarProjetosPorFuncionario(@Param("funcionarioId") Long funcionarioId);
}