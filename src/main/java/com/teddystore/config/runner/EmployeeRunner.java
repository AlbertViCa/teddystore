package com.teddystore.config.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(4)
public class EmployeeRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
