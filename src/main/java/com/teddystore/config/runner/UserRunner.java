package com.teddystore.config.runner;

import com.teddystore.model.Costumer;
import com.teddystore.repository.AuthorityRepository;
import com.teddystore.service.CostumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRunner implements CommandLineRunner {

    private final CostumerService costumerService;

    private  final PasswordEncoder encoder;

    @Autowired
    public UserRunner(CostumerService costumerService, PasswordEncoder encoder, AuthorityRepository authorityRepository) {
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

        log.info("---------- REGISTERING COSTUMER ----------");

        costumerService.registerCostumer(costumer);
        costumerService.registerCostumer(costumer2);

    }
}
