package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.springframework.stereotype.Service;

@Service
public class orderServiceImpl implements orderService {

    private final orderRepository orderRepository1;

    public orderServiceImpl(orderRepository orderRepository) {
        orderRepository1 = orderRepository;

    }

    @Override
    public orderDTOResponse createOrder(orderDTORequest order) {

        Order order1 = orderMapper.toOrderEntity(order);
        orderRepository1.save(order1);

        return orderMapper.toDTOResponse(order1);
    }
}
