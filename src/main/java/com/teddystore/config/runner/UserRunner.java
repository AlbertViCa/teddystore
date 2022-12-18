package com.teddystore.config.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teddystore.model.Authority;
import com.teddystore.model.Customer;
import com.teddystore.model.Employee;
import com.teddystore.model.WebAppUser;
import com.teddystore.service.AuthorityService;
import com.teddystore.service.CustomerService;
import com.teddystore.service.EmployeeService;
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

    private final CustomerService customerService;

    private final EmployeeService employeeService;

    private final AuthorityService authorityService;

    @Autowired
    public UserRunner(CustomerService customerService, EmployeeService employeeService, AuthorityService authorityService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.authorityService = authorityService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Authority> customerAuthorities = authorityService.findAll();

        customerAuthorities.remove(0);

        log.info("---------- CREATING USERS ----------");

        WebAppUser employee = Employee.builder()
                .firstName("Miguel Angel")
                .lastName("Fernandez")
                .secondLastName("Alvarez")
                .username("Miguel")
                .password("123")
                .phoneNumber("492 124 9232")
                .email("miguel@gmail.com")
                .authorities(authorityService.findAll())
                .build();

        WebAppUser customer = Customer.builder()
                .firstName("Alberto")
                .lastName("Villalpando")
                .secondLastName("Cardona")
                .username("Alberto")
                .password("123")
                .phoneNumber("492 123 9832")
                .email("alberto@gmail.com")
                .authorities(authorityService.findAll())
                .build();

        WebAppUser customer2 = Customer.builder()
                .firstName("Andrea")
                .lastName("Barragán")
                .secondLastName("González")
                .username("Andrea")
                .password("123")
                .phoneNumber("492 931 9832")
                .email("andrea@gmail.com")
                .authorities(customerAuthorities)
                .build();

        WebAppUser customer3 = Customer.builder()
                .firstName("María Esmeralda")
                .lastName("Pacheco")
                .secondLastName("González")
                .username("Esmeralda")
                .password("123")
                .phoneNumber("492 935 9832")
                .email("esmeralda@gmail.com")
                .authorities(customerAuthorities)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        log.info("---------- REGISTERING USERS ---------- \n{}, \n{}, \n{}, \n{}",
                mapper.writeValueAsString(employee),
                mapper.writeValueAsString(customer),
                mapper.writeValueAsString(customer2),
                mapper.writeValueAsString(customer3));

        employeeService.registerEmployee(employee);
        customerService.registerCustomer(customer);
        customerService.registerCustomer(customer2);
        customerService.registerCustomer(customer3);
    }
}
