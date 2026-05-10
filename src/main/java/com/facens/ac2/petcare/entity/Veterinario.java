package com.facens.ac2.petcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veterinarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 30)
    private String crmv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Especialidade especialidade;

    @JsonIgnore
    @OneToMany(mappedBy = "veterinario")
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();
}