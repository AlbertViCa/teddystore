package com.teddystore.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class BranchOfficeAddressRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
