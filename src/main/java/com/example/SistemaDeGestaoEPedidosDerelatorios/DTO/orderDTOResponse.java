package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class orderDTOResponse {

    @Getter
    @Setter
    private Long id;

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
