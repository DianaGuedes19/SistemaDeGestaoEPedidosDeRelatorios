package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.errorLog;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class errorLogDTOResponse {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private LocalDate loggedAt;

    @Getter
    @Setter
    private Order order;
}
