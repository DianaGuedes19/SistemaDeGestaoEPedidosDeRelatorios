package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface errorLogRepository extends JpaRepository<ErrorLog,Long> {
}
