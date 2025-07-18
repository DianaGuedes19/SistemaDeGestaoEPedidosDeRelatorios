package com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderStatusHistory;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class orderStatusHistoryDTOResponse {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private State oldStates;

    @Getter
    @Setter
    private State newState;

    @Getter
    @Setter
    private Order order;
}
