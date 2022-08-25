package com.teddystore.config.runner;

import com.teddystore.service.TeddyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TeddyRunner implements CommandLineRunner {

    private final TeddyService teddyService;

    @Autowired
    public TeddyRunner(TeddyService teddyService) {
        this.teddyService = teddyService;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
