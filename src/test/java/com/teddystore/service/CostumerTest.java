package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Costumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
@ExtendWith(SpringExtension.class)
public class CostumerTest {

    private final CostumerService costumerService;

    private final MockMvc mockMvc;

    public CostumerTest(CostumerService costumerService, MockMvc mockMvc) {
        this.costumerService = costumerService;
        this.mockMvc = mockMvc;
    }

    @Test
    public void postCostumer() throws Exception {
        Costumer costumer = createCostumer();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/costumers/register/")
                        .with(jwt().authorities())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(12)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.fullName").value("Alberto Villalpando"))
                .andExpect(jsonPath("$.username").value("nova"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.phoneNumber").value("492 302 1303"))
                .andExpect(jsonPath("$.email").value("alberto@gmail.com"))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }

    @Test
    @WithAnonymousUser
    public void costumerNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/costumers/find-by-id/99/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin"))) //FIXME: NEEDS TO WORK WITH Scope_costumer:read
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound()).andReturn().getResponse();
    }

    @Test
    @WithAnonymousUser
    public void updateCostumer() throws Exception {
        Costumer costumer = createCostumer();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/costumers/register/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_costumer:write")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer)))
                        .andDo(print());

        Costumer costumer1 = createCostumer();
        costumer1.setFullName("Cristian Cruz");
        costumer1.setEmail("cristian@gmail.com");

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/costumers/update-details/4/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_admin"))) //FIXME: NEEDS TO WORK WITH Scope_costumer:update
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
                .username("nova")
                .password("123")
                .phoneNumber("492 302 1303")
                .email("alberto@gmail.com")
                .build();
    }
}
