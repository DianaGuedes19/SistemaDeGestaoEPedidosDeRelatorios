package com.example.SistemaDeGestaoEPedidosDerelatorios.controller;

import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTORequest;
import com.example.SistemaDeGestaoEPedidosDerelatorios.DTO.order.orderDTOResponse;
import com.example.SistemaDeGestaoEPedidosDerelatorios.domain.State;
import com.example.SistemaDeGestaoEPedidosDerelatorios.service.orderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class orderController {

    private final orderService orderService1;

    public orderController(orderService orderService) {
        this.orderService1 = orderService;
    }

    @PostMapping()
    public ResponseEntity<orderDTOResponse> createOrder (@RequestBody orderDTORequest orderRequest){
        orderDTOResponse orderDTO = orderService1.createOrder(orderRequest);

        if (!orderDTO.getClientValid()){
            return ResponseEntity.badRequest().body(orderDTO);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
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
