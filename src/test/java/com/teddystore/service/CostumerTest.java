package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Costumer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
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
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CostumerTest {

    private final CostumerService costumerService;

    private final MockMvc mockMvc;

    private static final Long UPDATE_DETAILS_ID = 5L;
    private static final Long FIND_ID = 1L;
    private static final Long DELETE_ID = 2L;
    private static final Long NOT_FOUND_ID = (long) Integer.MAX_VALUE;
    private static final Long FORBIDDEN_ID = 3L;

    @Autowired
    public CostumerTest(CostumerService costumerService, MockMvc mockMvc) {
        this.costumerService = costumerService;
        this.mockMvc = mockMvc;
    }

    /**
     * <strong><font color="green">Happy Path</font></strong>
     * */

    @Test
    @Order(1)
    @DisplayName("POST Costumer and then CREATED (201)")
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
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }

    @Test
    @Order(2)
    @WithAnonymousUser
    @DisplayName("GET Costumer and then FOUND (302)")
    public void costumerFound() throws Exception {
        mockMvc.perform(get("/api/v1/costumers/find-by-id/" + FIND_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.fullName").value("Alberto Villalpando"))
                .andExpect(jsonPath("$.username").value("Alberto"))
                .andExpect(jsonPath("$.phoneNumber").value("492 123 9832"))
                .andExpect(jsonPath("$.email").value("albert@gmail.com"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    @DisplayName("PUT Costumer and then CREATED (201)")
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

        Optional<Costumer> result = costumerService.getCostumerById(UPDATE_DETAILS_ID);
        Costumer costumer1 = result.orElse(null);
        assert costumer1 != null;
        costumer1.setFullName("Cristian Cruz");
        costumer1.setEmail("cristian@gmail.com");

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/costumers/update-details/" + UPDATE_DETAILS_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH UPDATE.
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(costumer1)))
                .andDo(print())
                .andExpect(jsonPath("$.fullName").value("Cristian Cruz"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(costumerService.getCostumerById(id));
    }

    @Test
    @Order(4)
    @WithAnonymousUser
    @DisplayName("DELETE Costumer and then NO CONTENT (204)")
    public void deleteCostumer() throws Exception {
        mockMvc.perform(delete("/api/v1/costumers/delete-by-id/" + DELETE_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("OWNER")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(5)
    @WithAnonymousUser
    @DisplayName("DELETE ALL Costumers and then NO CONTENT (204)")
    public void deleteAllCostumers() throws Exception {
        mockMvc.perform(delete("/api/v1/costumers/delete-all/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("OWNER")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
    }

    /**
     * <strong><font color="red">Unhappy Path</font></strong>
     * */

    @Test
    @Order(6)
    @WithAnonymousUser
    @DisplayName("GET Costumer and then NOT FOUND (404)")
    public void costumerNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/costumers/find-by-id/" + NOT_FOUND_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(7)
    @WithMockUser
    @DisplayName("DELETE Costumer and then FORBIDDEN (403)")
    public void deleteCostumerNotAuthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/costumers/delete-by-id/" + FORBIDDEN_ID + "/")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(8)
    @WithAnonymousUser
    @DisplayName("DELETE ALL Costumers and then FORBIDDEN (403)")
    public void deleteAllCostumersNotAuthorized() throws Exception {
        mockMvc.perform(delete("/api/v1/costumers/delete-all/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("DELETE")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();
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
