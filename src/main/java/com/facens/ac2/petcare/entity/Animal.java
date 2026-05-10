package com.facens.ac2.petcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 80)
    private String especie;

    @Column(nullable = false, length = 80)
    private String raca;

    @Column(nullable = false)
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PorteAnimal porte;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @JsonIgnore
    @OneToMany(mappedBy = "animal")
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "animal")
    @Builder.Default
    private List<Prontuario> prontuarios = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "animal")
    @Builder.Default
    private List<RegistroVacinacao> vacinacoes = new ArrayList<>();
}