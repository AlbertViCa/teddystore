package com.teddystore.config.runner;

import com.teddystore.model.Teddy;
import com.teddystore.service.TeddyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@Order(2)
public class TeddyRunner implements CommandLineRunner {

    private final TeddyService teddyService;

    @Autowired
    public TeddyRunner(TeddyService teddyService) {
        this.teddyService = teddyService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("---------- CREATING TEDDY ----------");

        Teddy teddy = Teddy.builder()
                .name("Bard")
                .details("Snow Fest Bard")
                .size(15.00)
                .price(BigDecimal.valueOf(300.00))
                .imageURL("image.com")
                .build();

        log.info("---------- SAVING TEDDY ----------");

        teddyService.saveTeddy(teddy);
    }
}
