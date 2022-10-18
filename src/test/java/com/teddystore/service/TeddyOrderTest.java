package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Customer;
import com.teddystore.model.Teddy;
import com.teddystore.model.TeddyOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeddyOrderTest {

    private final TeddyOrderService teddyOrderService;

    private final TeddyService teddyService;

    private final CustomerService customerService;

    private final MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1/orders/";

    @Autowired
    public TeddyOrderTest(TeddyOrderService teddyOrderService, TeddyService teddyService, CustomerService customerService, MockMvc mockMvc) {
        this.teddyOrderService = teddyOrderService;
        this.teddyService = teddyService;
        this.customerService = customerService;
        this.mockMvc = mockMvc;
    }

    @Test
    @Order(1)
    @WithAnonymousUser
    public void createOrderAndThenCreated() throws Exception {
        Customer customer = customerService.getCustomerById(1L).orElse(null);
        assert customer != null;

        Teddy teddy = teddyService.getById(1L).orElse(null);
        assert teddy != null;

        TeddyOrder teddyOrder = TeddyOrder.builder()
                .teddies(List.of(teddy))
                .customer(customer)
                .totalCost(BigDecimal.valueOf(250.00))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH + "create/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("WRITE")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(teddyOrder)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(teddyOrderService.getOrderById(id));
    }
}
