package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.Consulta;
import com.facens.ac2.petcare.entity.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByVeterinarioIdAndDataHoraAndStatusNot(
            Long veterinarioId,
            LocalDateTime dataHora,
            StatusConsulta status
    );

    List<Consulta> findByAnimalId(Long animalId);

    List<Consulta> findByVeterinarioId(Long veterinarioId);
}