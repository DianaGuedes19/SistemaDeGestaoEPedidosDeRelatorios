package com.example.SistemaDeGestaoEPedidosDerelatorios.controller;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.service.orderService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.mockito.Mock;



import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class orderControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Mock
    private orderService orderService1;

    @InjectMocks
    private orderController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);


        objectMapper = new ObjectMapper()
                .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
                .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    void createOrder_deveRetornar201EODTO_NoBody() throws Exception {
        // --- Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Diana");
        req.setClientEmail("diana@gmail.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(130.2);

        orderDTOResponse resp = new orderDTOResponse();
        resp.setId(1L);
        resp.setClientName("Diana");
        resp.setClientEmail("diana@gmail.com");
        resp.setCreationDate(req.getCreationDate());
        resp.setStatus(req.getStatus());
        resp.setValue(req.getValue());

        resp.setClientValid(true);

        resp.setValidationMessage(null);


        when(orderService1.createOrder(any(orderDTORequest.class)))
                .thenReturn(resp);

        // Act & Assert
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(resp)));
    }

    @Test
    void createOrder_deveRetornar400QuandoClienteInvalido() throws Exception {
        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Bob");
        req.setClientEmail("bob#no-at-sign.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(50.0);

        orderDTOResponse resp = new orderDTOResponse();
        resp.setClientValid(false);
        resp.setValidationMessage("Wrong Email");

        when(orderService1.createOrder(any(orderDTORequest.class)))
                .thenReturn(resp);

        // Act & Assert
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.clientValid").value(false))
                .andExpect(jsonPath("$.validationMessage").value("Wrong Email"));
    }

    @Test
    void getAllOrders_deveRetornar200EListaDeDTOs() throws Exception {
        // Arrange
        orderDTOResponse o1 = new orderDTOResponse();
        o1.setId(1L);
        o1.setClientName("Alice");
        o1.setClientEmail("alice@example.com");
        o1.setCreationDate(LocalDate.of(2025, 7, 17));
        o1.setStatus(State.PENDENTE);
        o1.setValue(50.0);

        orderDTOResponse o2 = new orderDTOResponse();
        o2.setId(2L);
        o2.setClientName("Bob");
        o2.setClientEmail("bob@example.com");
        o2.setCreationDate(LocalDate.of(2025, 7, 18));
        o2.setStatus(State.PENDENTE);
        o2.setValue(75.5);

        List<orderDTOResponse> lista = Arrays.asList(o1, o2);

        when(orderService1.getAllOders()).thenReturn(lista);

        // Act & Assert
        mockMvc.perform(get("/orders/allOrders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void getOrderByID_deveRetornar200EOrderDTO() throws Exception {
        // Arrange
        Long id = 1L;
        orderDTOResponse dto = new orderDTOResponse();
        dto.setId(id);
        dto.setClientName("Diana");
        dto.setClientEmail("diana@gmail.com");
        dto.setCreationDate(LocalDate.of(2025,7,17));
        dto.setStatus(State.PENDENTE);
        dto.setValue(130.2);

        when(orderService1.getOrderByID(id)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/orders/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getOrdersByStatus_deveRetornar200EListaPorStatus() throws Exception {
        // Arrange
        orderDTOResponse o1 = new orderDTOResponse();
        o1.setId(10L);
        o1.setClientName("Ana");
        o1.setClientEmail("ana@exemplo.com");
        o1.setCreationDate(LocalDate.of(2025, 7, 15));
        o1.setStatus(State.APROVADO);
        o1.setValue(200.0);

        orderDTOResponse o2 = new orderDTOResponse();
        o2.setId(11L);
        o2.setClientName("Bruno");
        o2.setClientEmail("bruno@exemplo.com");
        o2.setCreationDate(LocalDate.of(2025, 7, 16));
        o2.setStatus(State.APROVADO);
        o2.setValue(150.0);

        List<orderDTOResponse> lista = Arrays.asList(o1, o2);

        when(orderService1.findByStatus(State.APROVADO)).thenReturn(lista);

        // Act & Assert
        mockMvc.perform(get("/orders/status/{status}", "APROVADO")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }

    @Test
    void getOrdersByDate_deveRetornar200EListaPorData() throws Exception {
        // --- Arrange
        LocalDate dataPesquisa = LocalDate.of(2025, 7, 17);

        orderDTOResponse o1 = new orderDTOResponse();
        o1.setId(20L);
        o1.setClientName("Carlos");
        o1.setClientEmail("carlos@exemplo.com");
        o1.setCreationDate(dataPesquisa);
        o1.setStatus(State.PENDENTE);
        o1.setValue(90.0);

        orderDTOResponse o2 = new orderDTOResponse();
        o2.setId(21L);
        o2.setClientName("Daniela");
        o2.setClientEmail("daniela@exemplo.com");
        o2.setCreationDate(dataPesquisa);
        o2.setStatus(State.PENDENTE);
        o2.setValue(120.0);

        List<orderDTOResponse> lista = Arrays.asList(o1, o2);

        when(orderService1.findByCreationDate(dataPesquisa)).thenReturn(lista);

        // --- Act & Assert
        mockMvc.perform(get("/orders/date/{date}", dataPesquisa.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lista)));
    }


}