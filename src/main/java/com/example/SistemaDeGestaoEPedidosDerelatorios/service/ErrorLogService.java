package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogService {
    private final errorLogRepository repo;
    private final EmailService emailService;


    public ErrorLogService(errorLogRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public void logError(Exception e, Order order) {
        ErrorLog log = new ErrorLog();
        log.setMessage(e.getMessage());
        log.setTimestamp(LocalDateTime.now());
        log.setOrder(order);
        repo.save(log);

        String subject = "Erro no sistema: " + e.getClass().getSimpleName();
        String body = "Ocorreu um erro em " + LocalDateTime.now() + "\n\n" +
                "Mensagem: " + e.getMessage() + "\n" +
                "Pedido: " + (order != null ? order.getId() : "N/A") + "\n\n" +
                "Por favor verifique o log completo no servidor.";
        emailService.sendSimpleMessage(
                "dianaguedesmarketing@gmail.com",
                subject,
                body
        );;
    }
}
