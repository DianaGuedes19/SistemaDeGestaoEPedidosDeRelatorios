package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorReportService {

    private final errorLogRepository errorLogRepo;
    private final JavaMailSender mailSender;
    private final String reportRecipient = "dianaguedesmarketing@gmail.com";

    public ErrorReportService(
            errorLogRepository errorLogRepo,
            JavaMailSender mailSender
    ) {
        this.errorLogRepo = errorLogRepo;
        this.mailSender = mailSender;
    }

    public void sendDailyErrorReport() {

        LocalDate today = LocalDate.now();
        LocalDateTime from = today.minusDays(1).atStartOfDay();
        LocalDateTime to   = today.atStartOfDay();

        List<ErrorLog> logs = errorLogRepo.findByTimestampBetween(from, to);
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
        msg.setTo(reportRecipient);
        msg.setSubject("Daily Error Report");
        msg.setText(sb.toString());
        mailSender.send(msg);
    }
}
