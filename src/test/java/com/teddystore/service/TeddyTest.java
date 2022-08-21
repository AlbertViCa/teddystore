package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Teddy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TeddyService.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class TeddyTest {

    @MockBean
    private TeddyService teddyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postTeddy() throws Exception {
        Teddy teddy = Teddy.builder()
                .name("Teddy")
                .details("Teddy details")
                .size(15.00)
                .price(BigDecimal.valueOf(350.00))
                .imageURL("image.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/teddies/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_teddy:write")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(teddy)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Teddy"))
                .andExpect(jsonPath("$.details").value("Teddy details"))
                .andExpect(jsonPath("$.size").value(15.00))
                .andExpect(jsonPath("$.price").value(350.00))
                .andExpect(jsonPath("$.imageURL").value("image.com"))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(teddyService.getById(id));
    }
}
