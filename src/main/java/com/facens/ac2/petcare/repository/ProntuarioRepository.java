package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    List<Prontuario> findByAnimalId(Long animalId);

    boolean existsByConsultaId(Long consultaId);
}