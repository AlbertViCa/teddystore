package com.teddystore.controller;

import com.teddystore.model.TeddyOrder;
import com.teddystore.service.TeddyOrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders/")
public class TeddyOrderController {

    private final TeddyOrderService teddyOrderService;

    public TeddyOrderController(TeddyOrderService teddyOrderService) {
        this.teddyOrderService = teddyOrderService;
    }

    @PostMapping(value = "create/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody TeddyOrder teddyOrder) {
        teddyOrderService.createOrder(teddyOrder);
    }

    @GetMapping("{id}")
    public Optional<TeddyOrder> getOrderById(@PathVariable Long id) {
        return teddyOrderService.getOrderById(id);
    }

}
