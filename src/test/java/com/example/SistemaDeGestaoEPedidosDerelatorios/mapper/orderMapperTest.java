package com.example.SistemaDeGestaoEPedidosDerelatorios.mapper;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class orderMapperTest {

    @Test
    void toDTOResponse() {
        // Arrange
        Order order = new Order(1L, "Diana", "diana@gmail.com", LocalDate.now(), State.PENDENTE,130.2);

        // Act
        orderDTOResponse dto = orderMapper.toDTOResponse(order);

        // Assert
        assertEquals(order.getId(),          dto.getId());
        assertEquals(order.getClientName(),  dto.getClientName());
        assertEquals(order.getClientEmail(), dto.getClientEmail());
        assertEquals(order.getCreationDate(),dto.getCreationDate());
        assertEquals(order.getStatus(),      dto.getStatus());
        assertEquals(order.getValue(),       dto.getValue());
    }

    @Test
    void toOrderEntity() {
        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Diana");
        req.setClientEmail("diana@gmail.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(130.2);

        // Act
        Order entity = orderMapper.toOrderEntity(req);

        // Assert
        assertNull(entity.getId());
        assertEquals(req.getClientName(),   entity.getClientName());
        assertEquals(req.getClientEmail(),  entity.getClientEmail());
        assertEquals(req.getCreationDate(), entity.getCreationDate());
        assertEquals(req.getStatus(),       entity.getStatus());
        assertEquals(req.getValue(),        entity.getValue());
    }
    }
