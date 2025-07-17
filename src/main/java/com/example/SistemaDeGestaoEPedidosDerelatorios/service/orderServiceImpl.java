package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<orderDTOResponse> getAllOders() {

        List<Order> order = orderRepository1.findAll();
        return order.stream().map(orderMapper::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public orderDTOResponse getOrderByID(Long id) {

        Order order1 = orderRepository1.findById(id).orElseThrow(() -> new RuntimeException("Order not found " + id));

        return orderMapper.toDTOResponse(order1);
    }
}
