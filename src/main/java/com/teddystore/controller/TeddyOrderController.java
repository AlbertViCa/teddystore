package com.teddystore.controller;

import com.teddystore.model.Customer;
import com.teddystore.model.TeddyOrder;
import com.teddystore.service.TeddyOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasAuthority('WRITE')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TeddyOrder createOrder(@RequestBody TeddyOrder teddyOrder) {
        return teddyOrderService.createOrder(teddyOrder);
    }

    @GetMapping("find-by-id/{id}/")
    @PreAuthorize("hasAuthority('READ') or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<TeddyOrder> getOrderById(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        Long customerId = customer.getId();
        return teddyOrderService.findTeddyOrderByIdAndCustomerId(id, customerId);
    }
}
