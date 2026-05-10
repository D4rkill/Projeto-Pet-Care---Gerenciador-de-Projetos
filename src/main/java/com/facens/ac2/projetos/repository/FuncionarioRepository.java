package com.facens.ac2.projetos.repository;

import com.facens.ac2.projetos.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}