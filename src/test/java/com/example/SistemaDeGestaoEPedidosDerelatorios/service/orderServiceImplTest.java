package com.example.SistemaDeGestaoEPedidosDerelatorios.service;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.POJOS.emailListResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.Order;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.mapper.orderMapper;
import com.example.SistemaDeGestaoEPedidosDerelatorios.repository.orderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.when;

class orderServiceImplTest {

    @Mock
    private orderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    private final String listUrl = "https://mocki.io/v1/cf019581-c65d-4a33-a496-06c751fdc377";


    private orderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new orderServiceImpl(orderRepository,restTemplate,listUrl);

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

        emailListResponse listResp = new emailListResponse(
                Arrays.asList("diana@gmail.com", "outra@exemplo.com")
        );
        when(restTemplate.getForObject(eq(listUrl), eq(emailListResponse.class)))
                .thenReturn(listResp);

        Order savedEntity = orderMapper.toOrderEntity(req);
        savedEntity.setClientValid(true);
        savedEntity.setValidationMessage("Existing Client. Order created with success!");
        when(orderRepository.save(any(Order.class))).thenReturn(savedEntity);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertEquals("Diana", result.getClientName());
    }


    @Test
    void createOrder_nonExistingEmail_returnsBadDto() {
        // Arrange
        orderDTORequest req = new orderDTORequest();
        req.setClientName("João");
        req.setClientEmail("joao@invalido.com");
        req.setCreationDate(LocalDate.of(2025, 7, 17));
        req.setStatus(State.PENDENTE);
        req.setValue(50.0);

        emailListResponse listResp = new emailListResponse(
                Collections.singletonList("outra@exemplo.com")
        );
        when(restTemplate.getForObject(eq(listUrl), eq(emailListResponse.class)))
                .thenReturn(listResp);

        Order savedEntity = orderMapper.toOrderEntity(req);

        savedEntity.setClientValid(false);
        savedEntity.setValidationMessage("Client doesn't exist");
        when(orderRepository.save(any(Order.class))).thenReturn(savedEntity);

        // Act
        orderDTOResponse result = orderService.createOrder(req);

        // Assert
        assertNotNull(result);
        assertEquals("João", result.getClientName());
        assertEquals("Client doesn't exists", result.getValidationMessage());
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

    @Test
    void findByState_returnsDtosWhenFound() {
        // Arrange
        Order o1 = new Order(1L, "CliA", "a@exa.com", LocalDate.of(2025,7,18), State.PENDENTE, 10.0);
        Order o2 = new Order(2L, "CliB", "b@exa.com", LocalDate.of(2025,7,19), State.PENDENTE, 20.0);
        List<Order> domainList = Arrays.asList(o1, o2);

        when(orderRepository.findByStatus(State.PENDENTE))
                .thenReturn(domainList);

        // Act
        List<orderDTOResponse> result = orderService.findByStatus(State.PENDENTE);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(dto -> dto.getStatus() == State.PENDENTE && (dto.getClientName().equals("CliA") || dto.getClientName().equals("CliB"))));
    }

    @Test
    void findByState_returnsEmptyListWhenNoneFound() {
        // Arrange
        when(orderRepository.findByStatus(State.PENDENTE))
                .thenReturn(Collections.emptyList());

        // Act
        List<orderDTOResponse> result = orderService.findByStatus(State.PENDENTE);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCreationDate_returnsDtosWhenFound() {
        // Arrange
        LocalDate targetDate = LocalDate.of(2025, 7, 18);

        Order o1 = new Order(1L, "CliA", "a@exa.com", targetDate, State.PENDENTE, 10.0);
        Order o2 = new Order(2L, "CliB", "b@exa.com", targetDate, State.APROVADO, 20.0);
        List<Order> domainList = Arrays.asList(o1, o2);

        when(orderRepository.findByCreationDate(targetDate)).thenReturn(domainList);

        // Act
        List<orderDTOResponse> result = orderService.findByCreationDate(targetDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(dto -> dto.getCreationDate().equals(targetDate)));
    }

    @Test
    void findByCreationDate_returnsEmptyListWhenNoneFound() {
        // Arrange
        LocalDate targetDate = LocalDate.of(2025, 1, 1);
        when(orderRepository.findByCreationDate(targetDate)).thenReturn(Collections.emptyList());

        // Act
        List<orderDTOResponse> result = orderService.findByCreationDate(targetDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}