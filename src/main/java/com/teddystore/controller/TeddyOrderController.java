package com.teddystore.controller;

import com.teddystore.model.TeddyOrder;
import com.teddystore.service.TeddyOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders/")
public class TeddyOrderController {

    private final TeddyOrderService teddyOrderService;

    public TeddyOrderController(TeddyOrderService teddyOrderService) {
        this.teddyOrderService = teddyOrderService;
    }

    @PostMapping(value = "create/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeddyOrder createOrder(@RequestBody TeddyOrder teddyOrder) {
        return teddyOrderService.createOrder(teddyOrder);
    }

    @GetMapping("find-by-id/{id}/")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<TeddyOrder> getOrderById(@PathVariable Long id) {
        return teddyOrderService.getOrderById(id);
    }

}
