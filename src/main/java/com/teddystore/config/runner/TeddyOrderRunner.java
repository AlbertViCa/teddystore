package com.teddystore.config.runner;

import com.teddystore.exception.CostumerNotFoundException;
import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Teddy;
import com.teddystore.model.TeddyOrder;
import com.teddystore.service.CostumerService;
import com.teddystore.service.TeddyOrderService;
import com.teddystore.service.TeddyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Order(4)
public class TeddyOrderRunner implements CommandLineRunner {

    private final TeddyOrderService teddyOrderService;

    private final CostumerService costumerService;

    private final TeddyService teddyService;

    public TeddyOrderRunner(TeddyOrderService teddyOrderService, CostumerService costumerService, TeddyService teddyService) {
        this.teddyOrderService = teddyOrderService;
        this.costumerService = costumerService;
        this.teddyService = teddyService;
    }

    @Override
    public void run(String... args) throws Exception {

        Iterator<Teddy> result = Optional.ofNullable(teddyService.getAllTeddies()
                .orElseThrow(
                        () -> new TeddyNotFoundException("")))
                .get()
                .iterator();

        log.info("---------- CREATING ORDER ----------");

        List<Teddy> teddies = new ArrayList<>();

        result.forEachRemaining(teddies::add);

        TeddyOrder teddyOrder = TeddyOrder.builder()
                .teddies(teddies)
                .costumer(costumerService.getCostumerById(1L).orElseThrow(() -> new CostumerNotFoundException("")))
                .build();

        log.info("---------- SAVING ORDER ----------");

        teddyOrderService.createOrder(teddyOrder);
    }
}
