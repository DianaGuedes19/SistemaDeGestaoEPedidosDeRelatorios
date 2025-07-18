package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface orderRepository extends JpaRepository<Order,Long> {
    List<Order> findByStatus(State status);
    List<Order> findByCreationDate (LocalDate date);
}
