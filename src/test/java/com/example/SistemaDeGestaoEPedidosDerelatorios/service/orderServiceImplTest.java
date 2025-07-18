package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.ValidationResponse;
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

    private final String validationUrl = "https://run.mocky.io/v3/15b55d39-9a2c-4085-b542-05b098812a16";


    private orderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new orderServiceImpl(orderRepository,restTemplate, validationUrl);

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


        ValidationResponse fakeResp = new ValidationResponse(true, "Existing Client");
        ResponseEntity<ValidationResponse> fakeEntity = ResponseEntity.ok(fakeResp);
        when(restTemplate.postForEntity(eq(validationUrl), any(validationRequest.class), eq(ValidationResponse.class)))
                .thenReturn(fakeEntity);


        Order toSave = orderMapper.toOrderEntity(req);

        toSave.setClientValid(true);
        toSave.setValidationMessage("Existing Client");
        Order saved = new Order(1L,
                toSave.getClientName(),
                toSave.getClientEmail(),
                toSave.getCreationDate(),
                toSave.getStatus(),
                toSave.getValue());
        saved.setClientValid(true);
        saved.setValidationMessage("Existing Client");

        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertEquals("Diana", result.getClientName());
    }

    @Test
    void createOrder_whenValidationServiceFails_shouldReturnDtoWithError() {
        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("Diana");
        req.setClientEmail("diana@gmail.com");
        req.setCreationDate(LocalDate.of(2025,7,17));
        req.setStatus(State.PENDENTE);
        req.setValue(130.2);

        when(restTemplate.postForEntity(anyString(), any(validationRequest.class), eq(ValidationResponse.class))).thenThrow(new RestClientException("Timeout ao chamar validação"));

        Order saved = new Order(42L,req.getClientName(),req.getClientEmail(),req.getCreationDate(),req.getStatus(),req.getValue(),false,"Validation Error"  );


        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertFalse(result.getClientValid());
        assertTrue(result.getValidationMessage().startsWith("Validation Error"));

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