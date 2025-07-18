package com.example.SistemaDeGestaoEPedidosDerelatorios.repository;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface errorLogRepository extends JpaRepository<ErrorLog,Long> {
    List<ErrorLog> findByTimestampBetween(LocalDateTime from, LocalDateTime to);

}
