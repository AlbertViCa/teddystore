package com.teddystore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.teddystore.model.Customer;
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

import static org.hamcrest.Matchers.*;
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
public class CustomerTest {

    private final CustomerService customerService;

    private final MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1/customers/";
    private static final Long UPDATE_DETAILS_ID = 5L;
    private static final Long FIND_ID = 1L;
    private static final String FIND_USERNAME = "Alberto";
    private static final String USERNAME_NOT_FOUND = "Alejandro";
    private static final Long DELETE_ID = 2L;
    private static final Long NOT_FOUND_ID = (long) Integer.MAX_VALUE;
    private static final Long FORBIDDEN_ID = 3L;

    @Autowired
    public CustomerTest(CustomerService customerService, MockMvc mockMvc) {
        this.customerService = customerService;
        this.mockMvc = mockMvc;
    }

    /**
     * <strong><font color="green" size=6>Happy Path</font></strong>
     * */

    @Test
    @Order(1)
    @DisplayName("POST Customer and then CREATED (201)")
    public void postCustomerAndThenCreated() throws Exception {
        Customer customer = Customer.builder()
                .firstName("Alberto")
                .lastName("Villalpando")
                .secondLastName("Cardona")
                .username("Nova")
                .password("123")
                .phoneNumber("492 143 1303")
                .email("alberto99@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH + "register/")
                        .with(jwt().authorities())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.firstName").value("Alberto"))
                .andExpect(jsonPath("$.lastName").value("Villalpando"))
                .andExpect(jsonPath("$.username").value("Nova"))
                .andExpect(jsonPath("$.phoneNumber").value("492 143 1303"))
                .andExpect(jsonPath("$.email").value("alberto99@gmail.com"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(customerService.getCustomerById(id));
    }

    @Test
    @Order(2)
    @WithAnonymousUser
    @DisplayName("GET Customer by ID and then FOUND (302)")
    public void getCustomerAndThenFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-by-id/" + FIND_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.firstName").value("Alberto"))
                .andExpect(jsonPath("$.lastName").value("Villalpando"))
                .andExpect(jsonPath("$.username").value("Alberto"))
                .andExpect(jsonPath("$.phoneNumber").value("492 123 9832"))
                .andExpect(jsonPath("$.email").value("alberto@gmail.com"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    @DisplayName("GET Customer by USERNAME and then FOUND (302)")
    public void getCustomerByUsernameAndThenFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-by-username/" + FIND_USERNAME + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.firstName").value("Alberto"))
                .andExpect(jsonPath("$.lastName").value("Villalpando"))
                .andExpect(jsonPath("$.username").value("Alberto"))
                .andExpect(jsonPath("$.phoneNumber").value("492 123 9832"))
                .andExpect(jsonPath("$.email").value("alberto@gmail.com"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(4)
    @WithAnonymousUser
    @DisplayName("GET ALL Customers and then FOUND (302)")
    public void getAllCustomersAndThenFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-all/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].username").value("Alberto"))
                .andExpect(jsonPath("$.[1].id", equalTo(2)))
                .andExpect(jsonPath("$.[1].username").value("Andrea"))
                .andExpect(jsonPath("$.[2].id", equalTo(3)))
                .andExpect(jsonPath("$.[2].username").value("Esmeralda"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(5)
    @WithAnonymousUser
    @DisplayName("PUT Customer and then CREATED (201)")
    public void updateCustomerAndThenCreated() throws Exception {
        Customer customer = Customer.builder()
                .firstName("Alberto")
                .lastName("Villalpando")
                .secondLastName("Cardona")
                .username("Villalpando")
                .password("123")
                .phoneNumber("492 543 1023")
                .email("villalpando@gmail.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post(BASE_PATH + "register/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("WRITE")))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer)))
                        .andDo(print());

        Thread.sleep(2000);

        Optional<Customer> result = customerService.getCustomerById(UPDATE_DETAILS_ID);
        Customer customer1 = result.orElse(null);
        assert customer1 != null;
        customer1.setFirstName("Cristian");
        customer1.setLastName("Cruz");
        customer1.setSecondLastName("Delgado");
        customer1.setEmail("cristian@gmail.com");

        MockHttpServletResponse response = mockMvc.perform(put(BASE_PATH + "update-details/" + UPDATE_DETAILS_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH UPDATE.
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customer1)))
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value("Cristian"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        Long id = Long.valueOf(JsonPath.parse(response.getContentAsString()).read("$.id").toString());
        assertNotNull(customerService.getCustomerById(id));
    }

    @Test
    @Order(6)
    @WithAnonymousUser
    @DisplayName("DELETE Customer by ID and then NO CONTENT (204)")
    public void deleteCustomerAndThenNoContent() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "delete-by-id/" + DELETE_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("OWNER")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(7)
    @WithAnonymousUser
    @DisplayName("DELETE ALL Customers and then NO CONTENT (204)")
    public void deleteAllCustomersAndThenNoContent() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "delete-all/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("OWNER")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
    }

    /**
     * <strong><font color="red" size=6>Unhappy Path</font></strong>
     * */

    @Test
    @Order(8)
    @WithAnonymousUser
    @DisplayName("GET Customer by ID and then NOT FOUND (404)")
    public void getCostumerAndThenNotFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-by-id/" + NOT_FOUND_ID + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(9)
    @WithAnonymousUser
    @DisplayName("GET Customer by USERNAME and then NOT FOUND (404)")
    public void getCustomerByUsernameAndThenNotFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-by-username/" + USERNAME_NOT_FOUND + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ADMIN"))) //FIXME: NEEDS TO WORK WITH READ.
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(10)
    @WithAnonymousUser
    @DisplayName("GET Customer by USERNAME and then FORBIDDEN (403)")
    public void getCustomerByUsernameAndThenNotAuthorized() throws Exception {
        mockMvc.perform(get(BASE_PATH + "find-by-username/" + FIND_USERNAME + "/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("READ")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(11)
    @WithMockUser
    @DisplayName("DELETE Customer and then FORBIDDEN (403)")
    public void deleteCustomerAndThenNotAuthorized() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "costumers/delete-by-id/" + FORBIDDEN_ID + "/")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();
    }

    @Test
    @Order(12)
    @WithAnonymousUser
    @DisplayName("DELETE ALL Customers and then FORBIDDEN (403)")
    public void deleteAllCustomersAndThenNotAuthorized() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "delete-all/")
                        .with(jwt().authorities(new SimpleGrantedAuthority("DELETE")))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();
    }

    private Customer createCustomer() {
        return  Customer.builder()
                .firstName("Alberto")
                .lastName("Villalpando")
                .secondLastName("Cardona")
                .username("Nova")
                .password("123")
                .phoneNumber("492 143 1303")
                .email("alberto99@gmail.com")
                .build();
    }
}
