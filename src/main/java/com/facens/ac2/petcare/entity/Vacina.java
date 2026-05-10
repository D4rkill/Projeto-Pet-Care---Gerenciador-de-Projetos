package com.facens.ac2.petcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String nome;

    @Column(nullable = false, length = 120)
    private String fabricante;

    @Column(nullable = false)
    private Integer intervaloReforcoMeses;

    @JsonIgnore
    @OneToMany(mappedBy = "vacina")
    @Builder.Default
    private List<RegistroVacinacao> registros = new ArrayList<>();
}