package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderStatusHistoryRepository extends JpaRepository<OrderStatusHistory,Long> {
}
