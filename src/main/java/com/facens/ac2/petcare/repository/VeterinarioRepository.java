package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    boolean existsByCrmvIgnoreCase(String crmv);
}