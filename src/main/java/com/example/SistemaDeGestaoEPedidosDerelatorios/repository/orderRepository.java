package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepository extends JpaRepository<Order,Long> {
}
