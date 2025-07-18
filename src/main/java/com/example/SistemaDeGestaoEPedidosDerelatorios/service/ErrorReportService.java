package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ErrorReportService {
    private static final Logger logger = LoggerFactory.getLogger(ErrorReportService.class);

    @Value("${report.recipient}")
    private String reportRecipient;


    private final errorLogRepository errorLogRepo;
    private final JavaMailSender mailSender;


    public ErrorReportService(
            errorLogRepository errorLogRepo,
            JavaMailSender mailSender
    ) {
        this.errorLogRepo = errorLogRepo;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedRate = 60_000, initialDelay = 0)
    public void sendDailyErrorReport() {
        logger.info(">>> sendDailyErrorReport disparado em {}", LocalDateTime.now());



        LocalDate today = LocalDate.now();
        LocalDateTime to   = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(5);

        List<ErrorLog> logs = errorLogRepo.findByOccurredAtBetween(from, to);
        if (logs.isEmpty()) {
            return;
        }


        StringBuilder sb = new StringBuilder();
        sb.append("Daily error Report (")
                .append(from).append(" — ").append(to).append(")\n\n");

        for (ErrorLog e : logs) {
            sb.append("[").append(e.getOccurredAt()).append("] ")
                    .append(e.getException()).append(" em ")
                    .append(e.getPath()).append(": ")
                    .append(e.getMessage()).append("\n");
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("no-reply@example.com");
        msg.setTo("103e452782-07a967+user1@inbox.mailtrap.io");
        msg.setSubject("Daily Error Report");
        msg.setText(sb.toString());
        mailSender.send(msg);


    }
}
