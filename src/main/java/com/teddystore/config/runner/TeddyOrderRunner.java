package com.teddystore.config.runner;

import com.teddystore.exception.CustomerNotFoundException;
import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Teddy;
import com.teddystore.model.TeddyOrder;
import com.teddystore.service.CustomerService;
import com.teddystore.service.TeddyOrderService;
import com.teddystore.service.TeddyService;
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
public class TeddyOrderRunner implements CommandLineRunner {

    private final TeddyOrderService teddyOrderService;

    private final CustomerService customerService;

    private final TeddyService teddyService;

    public TeddyOrderRunner(TeddyOrderService teddyOrderService, CustomerService customerService, TeddyService teddyService) {
        this.teddyOrderService = teddyOrderService;
        this.customerService = customerService;
        this.teddyService = teddyService;
    }

    @Override
    public void run(String... args) throws Exception {

        Iterator<Teddy> result = teddyService.getAllTeddies()
                .orElseThrow(() -> new TeddyNotFoundException(""))
                .iterator();

        log.info("---------- CREATING ORDER ----------");

        List<Teddy> teddies = new ArrayList<>();

        result.forEachRemaining(teddies::add);

        TeddyOrder teddyOrder = TeddyOrder.builder()
                .teddies(teddies)
                .customer(customerService.getCustomerById(1L).orElseThrow(() -> new CustomerNotFoundException("")))
                .totalCost(BigDecimal.valueOf(250.00))
                .build();

        log.info("---------- SAVING ORDER ----------");

        teddyOrderService.createOrder(teddyOrder);
    }
}
