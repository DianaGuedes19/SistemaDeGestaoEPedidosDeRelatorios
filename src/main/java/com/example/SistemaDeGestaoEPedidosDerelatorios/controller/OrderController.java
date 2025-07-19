package com.example.SistemaDeGestaoEPedidosDerelatorios.controller;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.service.orderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final orderService orderService1;

    public OrderController(orderService orderService) {
        this.orderService1 = orderService;
    }

    @PostMapping()
    public ResponseEntity<?> createOrder (@RequestBody orderDTORequest orderRequest){

        try {
            orderDTOResponse dto = orderService1.createOrder(orderRequest);
            return ResponseEntity.status(CREATED).body(dto);
        }

        catch (IllegalArgumentException ex) {
            Map<String,String> err = Collections.singletonMap("message", ex.getMessage());
            return ResponseEntity.badRequest().body(err);
        }

    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<orderDTOResponse>> getAllOrders (){
        return new ResponseEntity<>(orderService1.getAllOders(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<orderDTOResponse> getOrderByID (@PathVariable Long id){
        return new ResponseEntity<>(orderService1.getOrderByID(id),HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<orderDTOResponse>> getOrderByStatus (@PathVariable State status){
        return new ResponseEntity<>(orderService1.findByStatus(status),HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<orderDTOResponse>> getOrderByStatus (@PathVariable LocalDate date){
        return new ResponseEntity<>(orderService1.findByCreationDate(date),HttpStatus.OK);
    }
}
