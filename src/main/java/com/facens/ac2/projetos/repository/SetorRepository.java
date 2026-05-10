package com.facens.ac2.projetos.repository;

import com.facens.ac2.projetos.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    Optional<Setor> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    @Query("""
           SELECT DISTINCT s
           FROM Setor s
           LEFT JOIN FETCH s.funcionarios
           """)
    List<Setor> listarSetoresComFuncionarios();
}