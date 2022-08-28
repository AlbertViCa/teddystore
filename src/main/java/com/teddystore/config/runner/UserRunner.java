package com.teddystore.config.runner;

import com.teddystore.model.Costumer;
import com.teddystore.service.CostumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class UserRunner implements CommandLineRunner {

    private final CostumerService costumerService;

    private  final PasswordEncoder encoder;

    @Autowired
    public UserRunner(CostumerService costumerService, PasswordEncoder encoder) {
        this.costumerService = costumerService;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("---------- CREATING COSTUMER ----------");

        Costumer costumer = Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("Alberto")
                .password(encoder.encode("123"))
                .phoneNumber("492 932 9832")
                .email("alberto@gmail.com")
                .authority("SCOPE_costumer:read")
                .build();

        Costumer costumer2 = Costumer.builder()
                .fullName("Roberto Esquivel")
                .username("Roberto")
                .password(encoder.encode("123"))
                .phoneNumber("492 932 9832")
                .email("roberto@gmail.com")
                .build();

        Costumer costumer3 = Costumer.builder()     //FIXME: ONLY EMPLOYEES CAN HAVE Scope_admin, IMPLEMENTED ONLY FOR TESTING PURPOSES
                .fullName("Andrés Martinez")
                .username("nova")
                .password(encoder.encode("123"))
                .phoneNumber("492 932 9832")
                .email("alberto@gmail.com")
                .authority("SCOPE_admin")
                .build();

        log.info("---------- REGISTERING COSTUMER ----------");

        costumerService.registerCostumer(costumer);
        costumerService.registerCostumer(costumer2);
        costumerService.registerCostumer(costumer3);

    }
}
