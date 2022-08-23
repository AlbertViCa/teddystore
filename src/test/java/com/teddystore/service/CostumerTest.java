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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CostumerTest {

    @Autowired
    private CostumerService costumerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postCostumer() throws Exception {
        Costumer costumer = Costumer.builder()
                .fullName("Alberto Villalpando")
                .username("nova")
                .password("123")
                .phoneNumber("492 302 1303")
                .email("alberto@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/costumers/register/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_costumer:write")))
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
    public void costumerNotFound() throws Exception {
        mockMvc.perform(get("/costumers/id/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_costumer:read")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound()).andReturn().getResponse();
    }
}
