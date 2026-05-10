package com.facens.ac2.petcare.repository;

import com.facens.ac2.petcare.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByEmailIgnoreCase(String email);
}