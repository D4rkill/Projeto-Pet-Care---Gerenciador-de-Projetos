package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    boolean existsByNomeIgnoreCase(String nome);
}