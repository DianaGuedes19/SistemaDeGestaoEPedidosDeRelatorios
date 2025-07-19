package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.ErrorLog;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.errorLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorLogServiceTest {

    @Mock
    private errorLogRepository repo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ErrorLogService service;

    @Captor
    private ArgumentCaptor<ErrorLog> logCaptor;

    @Captor
    private ArgumentCaptor<String> toCaptor;
    @Captor
    private ArgumentCaptor<String> subjectCaptor;
    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    void logError_semOrder_deveSalvarLogENotificarEmail() {
        // Arrange
        Exception ex = new RuntimeException("boom");

        // Act
        service.logError(ex, null);

        verify(repo).save(logCaptor.capture());
        ErrorLog saved = logCaptor.getValue();
        assertThat(saved.getMessage()).isEqualTo("boom");
        assertThat(saved.getOrder()).isNull();
        assertThat(saved.getTimestamp()).isNotNull();

        // eAsset
        verify(emailService).sendSimpleMessage(
                toCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture()
        );
        assertThat(toCaptor.getValue()).isEqualTo("dianaguedesmarketing@gmail.com");
        assertThat(subjectCaptor.getValue()).isEqualTo("Erro no sistema: RuntimeException");
        String body = bodyCaptor.getValue();
        assertThat(body).contains("Mensagem: boom");
        assertThat(body).contains("Pedido: N/A");
    }

    @Test
    void logError_comOrder_deveIncluirIdDoPedidoNoEmail() {
        // Arrange
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(42L);

        Exception ex = new IllegalArgumentException("invalid");

        // Act
        service.logError(ex, order);

        // Assert
        verify(repo).save(any());
        verify(emailService).sendSimpleMessage(
                anyString(),
                anyString(),
                bodyCaptor.capture()
        );
        assertThat(bodyCaptor.getValue()).contains("Pedido: 42");
    }

}