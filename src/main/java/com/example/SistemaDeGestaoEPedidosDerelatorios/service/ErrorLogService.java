package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogService {
    private final errorLogRepository repo;

    public ErrorLogService(errorLogRepository repo) {
        this.repo = repo;
    }


    public ErrorLog logError(Exception e, Order order) {
        ErrorLog log = new ErrorLog();
        log.setMessage(e.getMessage());
        log.setTimestamp(LocalDateTime.now());
        log.setOrder(order);  // pode ser nulo
        return repo.save(log);
    }
}
