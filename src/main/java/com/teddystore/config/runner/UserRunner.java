package com.teddystore.config.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teddystore.model.Authority;
import com.teddystore.model.Costumer;
import com.teddystore.service.AuthorityService;
import com.teddystore.service.CostumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(2)
public class UserRunner implements CommandLineRunner {

    private final CostumerService costumerService;

    private final AuthorityService authorityService;

    @Autowired
    public UserRunner(CostumerService costumerService, AuthorityService authorityService) {
        this.costumerService = costumerService;
        this.authorityService = authorityService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Authority> costumerAuthorities = authorityService.findAll();

        costumerAuthorities.remove(0);

        log.info("---------- CREATING COSTUMER ----------");

        Costumer costumer = Costumer.builder()
                .firstName("Alberto")
                .lastName("Villalpando")
                .secondLastName("Cardona")
                .username("Alberto")
                .password("123")
                .phoneNumber("492 123 9832")
                .email("alberto@gmail.com")
                .authorities(authorityService.findAll()) //FIXME: ONLY EMPLOYEES CAN HAVE ADMIN AUTHORITY, IMPLEMENTED ONLY FOR TESTING PURPOSES.
                .build();

        Costumer costumer2 = Costumer.builder()
                .firstName("Andrea")
                .lastName("Barragán")
                .secondLastName("González")
                .username("Andrea")
                .password("123")
                .phoneNumber("492 931 9832")
                .email("andrea@gmail.com")
                .authorities(costumerAuthorities)
                .build();

        Costumer costumer3 = Costumer.builder()
                .firstName("María Esmeralda")
                .lastName("Pacheco")
                .secondLastName("González")
                .username("Esmeralda")
                .password("123")
                .phoneNumber("492 935 9832")
                .email("esmeralda@gmail.com")
                .authorities(costumerAuthorities)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        log.info("---------- REGISTERING COSTUMER ---------- \n{}, \n{}, \n{}",
                mapper.writeValueAsString(costumer),
                mapper.writeValueAsString(costumer2),
                mapper.writeValueAsString(costumer3));

        costumerService.registerCostumer(costumer);
        costumerService.registerCostumer(costumer2);
        costumerService.registerCostumer(costumer3);

    }
}
