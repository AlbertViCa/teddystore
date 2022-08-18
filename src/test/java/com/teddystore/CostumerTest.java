package com.teddystore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Costumer;
import com.teddystore.services.CostumerService;
import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
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
        log.info("\n---------- CREATING USER ----------");
        Costumer costumer = Costumer.builder()
                .username("nova")
                .fullName("Alberto Villalpando")
                .password("123")
                .phoneNumber("492 302 1303")
                .email("alberto@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("\n---------- REGISTERING USER ----------");
        MockHttpServletResponse response = mockMvc.perform(post("/users/")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_user:write")))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(costumer)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.username").value("nova"))
                .andExpect(jsonPath("$.fullName").value("Alberto Villalpando"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.phoneNumber").value("492 302 1303"))
                .andExpect(jsonPath("$.email").value("alberto@gmail.com"))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }
}
