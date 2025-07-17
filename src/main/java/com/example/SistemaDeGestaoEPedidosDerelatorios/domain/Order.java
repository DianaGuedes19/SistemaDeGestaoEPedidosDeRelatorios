package com.example.SistemaDeGestaoEPedidosDerelatorios.domain;

import jakarta.persistence.*;
import jdk.vm.ci.meta.Local;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String clientName;

    @Getter
    private String clientEmail;

    @Getter
    private LocalDate creationDate;

    @Getter
    @Enumerated(EnumType.STRING)
    private State status;

    @Getter
    private double value;


}
