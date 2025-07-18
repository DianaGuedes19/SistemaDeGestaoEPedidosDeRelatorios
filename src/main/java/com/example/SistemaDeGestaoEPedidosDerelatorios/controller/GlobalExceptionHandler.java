package com.example.SistemaDeGestaoEPedidosDerelatorios.controller;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final errorLogRepository errorLogRepo;
    private final HttpServletRequest request;

    public GlobalExceptionHandler(errorLogRepository repo, HttpServletRequest req) {
        this.errorLogRepo = repo;
        this.request = req;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleAll(Exception ex) {

        ErrorLog log = new ErrorLog();
        log.setOccurredAt(LocalDateTime.now());
        log.setPath(request.getRequestURI());
        log.setException(ex.getClass().getSimpleName());
        log.setMessage(ex.getMessage());
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        log.setStackTrace(sw.toString());
        errorLogRepo.save(log);

        Map<String,String> body = Collections.singletonMap("message", ex.getMessage());
        HttpStatus status = (ex instanceof IllegalArgumentException)
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(body, status);
    }
}
