package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class orderServiceImplTest {

    @Mock
    private orderRepository orderRepository;


    private orderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new orderServiceImpl(orderRepository);
    }

    @Test
    void createOrder() {

        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Diana");
        req.setClientEmail("diana@gmail.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(130.2);


        Order entity = orderMapper.toOrderEntity(req);

        Order saved = new Order(1L,entity.getClientName(),entity.getClientEmail(),entity.getCreationDate(),entity.getStatus(),entity.getValue());
        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertEquals("Diana", result.getClientName());
    }

    @Test
    void getAllOrders() {

        // Arrange
        Order order1 = new Order(1L,"Diana", "diana@gmail.com",LocalDate.of(2025, 7, 17),State.PENDENTE,130.2);
        Order order2 = new Order(2L,"DianaG", "diana1@gmail.com",LocalDate.of(2025, 7, 17),State.PENDENTE,130.2);

        List<Order> domainList = new ArrayList<>();
        domainList.add(order1);
        domainList.add(order2);

        when(orderRepository.findAll()).thenReturn(domainList);

        //Act
        List<orderDTOResponse> orderResult = orderService.getAllOders();

        // Assert
        assertNotNull(orderResult);
        assertEquals(2, orderResult.size());


    }
}