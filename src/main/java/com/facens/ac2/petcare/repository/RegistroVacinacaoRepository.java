package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.RegistroVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroVacinacaoRepository extends JpaRepository<RegistroVacinacao, Long> {

    List<RegistroVacinacao> findByAnimalId(Long animalId);
}