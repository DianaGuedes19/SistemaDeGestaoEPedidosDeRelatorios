package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.awt.SystemColor.text;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class ErrorReportServiceTest {

    @Mock
    private errorLogRepository errorLogRepo;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private ErrorReportService service;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    private LocalDateTime from;
    private LocalDateTime to;

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.of(2025, 7, 19);
        from = today.minusDays(1).atStartOfDay();
        to   = today.atStartOfDay();


    }

    @Test
    void sendDailyErrorReport_shouldNotSend_whenNoLogs() {
        // Arrange
        when(errorLogRepo.findByTimestampBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class))
        )
                .thenReturn(Collections.emptyList());


        // Act
        service.sendDailyErrorReport();

        // Assert
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendDailyErrorReport_shouldSendReport_whenThereAreLogs() {
        // Arrange
        ErrorLog log1 = new ErrorLog();
        log1.setOccurredAt(from.plusHours(3));
        log1.setException("NullPointerException");
        log1.setPath("/orders");
        log1.setMessage("foo was null");

        ErrorLog log2 = new ErrorLog();
        log2.setOccurredAt(from.plusHours(7));
        log2.setException("IllegalArgumentException");
        log2.setPath("/clients");
        log2.setMessage("invalid id");

        List<ErrorLog> logs = Arrays.asList(log1, log2);
        when(errorLogRepo.findByTimestampBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class))
        ).thenReturn(logs);
        // Act
        service.sendDailyErrorReport();


        // Assert
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sent = messageCaptor.getValue();

        String[] recipients = sent.getTo();
        assertArrayEquals(
                new String[]{"dianaguedesmarketing@gmail.com"},
                recipients,
                "Deve enviar para o dianaguedesmarketing@gmail.com"
        );

        // assunto
        assertEquals(
                "Daily Error Report",
                sent.getSubject(),
                "Assunto deve ser 'Daily Error Report'"
        );


        String body = sent.getText();
        assertNotNull(body);
        assertTrue(
                body.contains("Daily error Report"),
                "Corpo deve ter o cabeçalho"
        );

        assertTrue(
                body.contains(
                        "[" + log1.getOccurredAt() + "] NullPointerException em /orders: foo was null"
                ),
                "Deve conter o primeiro log"
        );
        assertTrue(
                body.contains(
                        "[" + log2.getOccurredAt() + "] IllegalArgumentException em /clients: invalid id"
                ),
                "Deve conter o segundo log"
        );
    }

}