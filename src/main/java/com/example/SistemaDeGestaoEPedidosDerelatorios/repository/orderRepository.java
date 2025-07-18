package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderRepository extends JpaRepository<Order,Long> {
    List<Order> findByState(State status);
}
