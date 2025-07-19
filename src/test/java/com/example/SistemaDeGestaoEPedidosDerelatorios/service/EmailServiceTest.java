package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;


    @Test
    void sendSimpleMessage_deveEnviarEmailComOsDadosCorretos() {

            // Arrange
            String to      = "destino@exemplo.com";
            String subject = "Assunto de Teste";
            String text    = "Corpo da mensagem";

            // Act
            emailService.sendSimpleMessage(to, subject, text);

            // Assert
            ArgumentCaptor<SimpleMailMessage> captor =
                    ArgumentCaptor.forClass(SimpleMailMessage.class);
            verify(mailSender).send(captor.capture());

            SimpleMailMessage sent = captor.getValue();
            assertThat(sent.getTo()).containsExactly(to);
            assertThat(sent.getSubject()).isEqualTo(subject);
            assertThat(sent.getText()).isEqualTo(text);

    }


}