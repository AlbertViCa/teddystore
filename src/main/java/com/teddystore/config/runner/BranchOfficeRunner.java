package com.teddystore.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(8)
public class BranchOfficeRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
