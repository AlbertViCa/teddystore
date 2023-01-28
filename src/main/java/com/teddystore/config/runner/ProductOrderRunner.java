package com.teddystore.config.runner;

import com.teddystore.exception.CustomerNotFoundException;
import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Product;
import com.teddystore.model.ProductOrder;
import com.teddystore.service.CustomerService;
import com.teddystore.service.ProductOrderService;
import com.teddystore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@Order(4)
public class ProductOrderRunner implements CommandLineRunner {

    private final ProductOrderService productOrderService;

    private final CustomerService customerService;

    private final ProductService productService;

    public ProductOrderRunner(ProductOrderService productOrderService, CustomerService customerService, ProductService productService) {
        this.productOrderService = productOrderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        Iterator<Product> result = productService.getAllProducts()
                .orElseThrow(() -> new TeddyNotFoundException(""))
                .iterator();

        log.info("---------- CREATING ORDER ----------");

        List<Product> products = new ArrayList<>();

        result.forEachRemaining(products::add);

        ProductOrder productOrder = ProductOrder.builder()
                .products(products)
                .customer(customerService.getCustomerById(1L).orElseThrow(() -> new CustomerNotFoundException("")))
                .totalCost(BigDecimal.valueOf(250.00))
                .build();

        log.info("---------- SAVING ORDER ----------");

        productOrderService.createOrder(productOrder);
    }
}
