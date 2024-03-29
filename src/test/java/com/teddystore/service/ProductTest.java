package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Product;
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

import java.math.BigDecimal;

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
public class ProductTest {

    private final ProductService productService;

    private final MockMvc mockMvc;

    @Autowired
    public ProductTest(ProductService productService, MockMvc mockMvc) {
        this.productService = productService;
        this.mockMvc = mockMvc;
    }

    @Test
    @WithAnonymousUser
    public void postProduct() throws Exception {
        Product product = Product.builder()
                .name("Teddy")
                .details("Teddy details")
                .size(15.00)
                .price(BigDecimal.valueOf(350.00))
                .imageURL("image.com")
                .category(Product.Category.TEDDY)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/teddies/create/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("WRITE")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Teddy"))
                .andExpect(jsonPath("$.details").value("Teddy details"))
                .andExpect(jsonPath("$.size").value(15.00))
                .andExpect(jsonPath("$.price").value(350.00))
                .andExpect(jsonPath("$.imageURL").value("image.com"))
                .andExpect(status().isCreated()).andReturn().getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(productService.getProductById(id));
    }

    @Test
    @WithAnonymousUser
    public void productFound() throws Exception {
        mockMvc.perform(get("/api/v1/teddies/find-by-id/1/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("READ")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Bard"))
                .andExpect(status().isFound()).andReturn().getResponse();
    }

    @Test
    @WithAnonymousUser
    public void productNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/teddies/find-by-id/99/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("READ")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound()).andReturn().getResponse();
    }
}
