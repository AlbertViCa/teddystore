package com.teddystore.controller;

import com.teddystore.model.Customer;
import com.teddystore.model.ProductOrder;
import com.teddystore.service.ProductOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders/")
public class TeddyOrderController {

    private final ProductOrderService productOrderService;

    public TeddyOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping(value = "create/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductOrder createOrder(@RequestBody ProductOrder productOrder) {
        return productOrderService.createOrder(productOrder);
    }

    @GetMapping("find-by-id/{id}/")
    @PreAuthorize("hasAuthority('READ') or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<ProductOrder> getOrderById(@PathVariable Long id, @AuthenticationPrincipal Customer customer) {
        Long customerId = customer.getId();
        return productOrderService.findProductOrderByIdAndCustomerId(id, customerId);
    }
}
