package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.ValidationResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.emailListResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.validationRequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class orderServiceImplTest {

    @Mock
    private orderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    private final String validationUrl = "https://mocki.io/v1/f2458006-db81-453f-a250-b296a495adb3";
    private final String listUrl = "https://mocki.io/v1/cf019581-c65d-4a33-a496-06c751fdc377";


    private orderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new orderServiceImpl(orderRepository,restTemplate, validationUrl,listUrl);

    }

    @Test
    void createOrder() {

        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Diana");
        req.setClientEmail("diana@gmail.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(130.2);


        emailListResponse listResp = new emailListResponse(Arrays.asList("diana@gmail.com", "outra@exemplo.com"));
        when(restTemplate.getForObject(eq(listUrl), eq(emailListResponse.class)))
                .thenReturn(listResp);


        ValidationResponse fakeValidation = new ValidationResponse(true, "Existing Client");
        when(restTemplate.getForObject(eq(validationUrl), eq(ValidationResponse.class)))
                .thenReturn(fakeValidation);


        Order saved = orderMapper.toOrderEntity(req);
        saved.setClientValid(true);
        saved.setValidationMessage(null);
        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertEquals("Diana", result.getClientName());
    }



    @Test
    void getAllOrders() {

        // Arrange
        Order order1 = new Order(1L,"Diana", "diana@gmail.com",LocalDate.of(2025, 7, 17),State.PENDENTE,130.2);
        Order order2 = new Order(2L,"DianaG", "diana1@gmail.com",LocalDate.of(2025, 7, 17),State.PENDENTE,130.2);

        List<Order> domainList = new ArrayList<>();
        domainList.add(order1);
        domainList.add(order2);

        when(orderRepository.findAll()).thenReturn(domainList);

        //Act
        List<orderDTOResponse> orderResult = orderService.getAllOders();

        // Assert
        assertNotNull(orderResult);
        assertEquals(2, orderResult.size());

    }


    @Test
    void getOrderByID_deveRetornarDTOQuandoExistir() {
        // Arrange
        Long id = 42L;
        Order domain = new Order(42L, "Alice", "alice@example.com",LocalDate.of(2025, 7, 17),State.PENDENTE,99.99);

        when(orderRepository.findById(id)).thenReturn(Optional.of(domain));

        // Act
        orderDTOResponse result = orderService.getOrderByID(id);

        // Assert
        assertNotNull(result);
        assertEquals("Alice",result.getClientName());
}

    @Test
    void getOrderByID_deveLancarExceptionQuandoNaoExistir() {
        // Arrange
        Long id = 99L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.getOrderByID(id));
        assertEquals("Order not found " + id, ex.getMessage());

    }
}