package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class orderDTORequest {

    @Getter
    @Setter
    private String clientName;

    @Getter
    @Setter
    private String clientEmail;

    @Getter
    @Setter
    private LocalDate creationDate;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private State status;

    @Getter
    @Setter
    private double value;
}
