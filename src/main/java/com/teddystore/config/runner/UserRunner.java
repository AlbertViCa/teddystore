package com.teddystore.config.runner;

import com.teddystore.model.Authority;
import com.teddystore.model.Costumer;
import com.teddystore.service.AuthorityService;
import com.teddystore.service.CostumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(2)
public class UserRunner implements CommandLineRunner {

    private final CostumerService costumerService;

    private  final PasswordEncoder encoder;

    private final AuthorityService authorityService;

    @Autowired
    public UserRunner(CostumerService costumerService, PasswordEncoder encoder, AuthorityService authorityService) {
        this.costumerService = costumerService;
        this.encoder = encoder;
        this.authorityService = authorityService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Authority> costumerAuthorities = authorityService.findAll();

        costumerAuthorities.remove(4);

        log.info("---------- CREATING COSTUMER ----------");

        Costumer costumer = Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("Alberto")
                .password("123")
                .phoneNumber("492 123 9832")
                .email("albert@gmail.com")
                .authorities(authorityService.findAll())
                .build();

        Costumer costumer2 = Costumer.builder()
                .fullName("Roberto Esquivel")
                .username("Roberto")
                .password("123")
                .phoneNumber("492 931 9832")
                .email("roberto@gmail.com")
                .authorities(costumerAuthorities)
                .build();

        Costumer costumer3 = Costumer.builder()     //FIXME: ONLY EMPLOYEES CAN HAVE Scope_admin, IMPLEMENTED ONLY FOR TESTING PURPOSES
                .fullName("Andrés Martinez")
                .username("Andres")
                .password("123")
                .phoneNumber("492 935 9832")
                .email("andres@gmail.com")
                .authorities(costumerAuthorities)
                .build();

        log.info("---------- REGISTERING COSTUMER ----------");

        costumerService.registerCostumer(costumer);
        costumerService.registerCostumer(costumer2);
        costumerService.registerCostumer(costumer3);

    }
}
