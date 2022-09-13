package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Costumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class) //TODO: MAKE ID REQUEST VALUES FINAL PARAMETERS.
public class CostumerTest {

    private final CostumerService costumerService;

    private final MockMvc mockMvc;

    @Autowired
    public CostumerTest(CostumerService costumerService, MockMvc mockMvc) {
        this.costumerService = costumerService;
        this.mockMvc = mockMvc;
    }

    @Test
    public void postCostumer() throws Exception {
        Costumer costumer = Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("Nova")
                .password("123")
                .phoneNumber("492 143 1303")
                .email("alberto99@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/costumers/register/")
                        .with(jwt().authorities())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.fullName").value("Alberto Villalpando"))
                .andExpect(jsonPath("$.username").value("Nova"))
                .andExpect(jsonPath("$.phoneNumber").value("492 143 1303"))
                .andExpect(jsonPath("$.email").value("alberto99@gmail.com"))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }

    @Test
    @WithAnonymousUser
    public void costumerNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/costumers/find-by-id/99/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound()).andReturn().getResponse();
    }

    @Test
    @WithAnonymousUser
    public void updateCostumer() throws Exception {
        Costumer costumer = Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("Villalpando")
                .password("123")
                .phoneNumber("492 543 1023")
                .email("villalpando@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/costumers/register/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("WRITE")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer)))
                        .andDo(print());

        Optional<Costumer> result = costumerService.getCostumerById(4L);
        Costumer costumer1 = result.get();
        costumer1.setFullName("Cristian Cruz");
        costumer1.setEmail("cristian@gmail.com");

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/costumers/update-details/4/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH UPDATE.
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer1)))
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value("Cristian Cruz"))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }

    private Costumer createCostumer() {
        return  Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("Nova")
                .password("123")
                .phoneNumber("492 143 1303")
                .email("alberto99@gmail.com")
                .build();
    }
}
