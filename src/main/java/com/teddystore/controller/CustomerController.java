package com.teddystore.controller;

import com.teddystore.model.Customer;
import com.teddystore.repository.CustomerRepository;
import com.teddystore.service.CustomerService;
import com.teddystore.service.CustomerServiceImp;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;
/**
 * @author Alberto Villalpando <br><br>
 * Clase a la que se le harán peticiones desde un cliente a través de peticiones <strong>CRUD <font color="#85ba6a">{@code POST, GET, PUT, DELETE}</font></strong>. <br><br>
 * <font color="#85ba6a"><strong>{@code @RestController}</strong></font>: Transforma la clase para que esta se encargue de exponer la información en una API. <br><br>
 * <font color="#85ba6a"><strong>{@code @RequestMapping}</strong></font>: Ruta base a la que se le harán peticiones a esta clase. <br> Ej: http://localhost:8080<strong>/api/v1/customers/find-by-id/1/</strong> <br><br>
 * <font color="#85ba6a"><strong>{@code @PostMapping}</strong></font>: Método que se enacarga de crear o persistir información en la API. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code value}</strong></font>: Ruta para solicirar el método. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code consumes}</strong></font>: Establece que el método recibe información en formato JSON. <br><br>
 * <font color="#85ba6a"><strong>{@code @GetMapping}</strong></font>: Método que se enacarga de obtener información de la API. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code value}</strong></font>: Ruta para solicirar el método. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code produces}</strong></font>: Establece que el método retorna información en formato JSON. <br><br>
 * <font color="#85ba6a"><strong>{@code @PutMapping}</strong></font>: Método que se enacarga de actualizar información existente en la API. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code value}</strong></font>: Ruta para solicirar el método. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code consumes}</strong></font>: Establece que el método recibe información en formato JSON. <br><br>
 * <font color="#85ba6a"><strong>{@code @DeleteMapping}</strong></font>: Método que se enacarga de eliminar información de la API. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code value}</strong></font>: Ruta para solicirar el método. <br><br>
 * <font color="#85ba6a"><strong>{@code @ResponseStatus}</strong></font>: Código HTTP que retorna el método en caso de ser exitoso. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code FOUND}</strong></font>: Retorna 302 Found. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code CREATED}</strong></font>: Retorna 201 Created. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code NO_CONTENT}</strong></font>: Retorna 204 No Content (la operación se realizó con éxito, pero no retorna cuerpo). <br><br>
 * <font color="#85ba6a"><strong>{@code @PreAuthorize}</strong></font>: Verifica que el usuario solicitando la información tenga los privilegios requeridos. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code #customer.id == #id}</strong></font>: Verifica que el usuario solicitando la información le corresponda a él, evita que usuarios no autorizados accedan a la información de otros. <br><br>
 * <font color="#85ba6a"><strong>{@code @RequestBody}</strong></font>: El método espera que el JSON enviado desde el cliente sea en el formato de este objeto. <br><br>
 * <font color="#85ba6a"><strong>{@code @PathVariable}</strong></font>: Obtiene el dato ingresado en la ruta. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code find-by-id/{id}/ -> find-by-id/1/}</strong></font><br><br>
 * <font color="#85ba6a"><strong>{@code @AuthenticationPrincipal}</strong></font>: Obtiene la información del usario que solicita el método, necesario para la anotación <font color="#85ba6a"><strong>{@code @PreAuthorize}</strong></font>. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Customer}
 *              -> {@link CustomerRepository  CustomerRepository}
 *              -> {@link CustomerService}
 *              -> {@link CustomerServiceImp}
 *              -> {@link CustomerController}
 * </strong>
 * @see <a href="https://livebook.manning.com/book/spring-quickly/chapter-10/">Spring Start Here: 10 Implementing REST services</a>
 * @see <a href="https://livebook.manning.com/book/spring-boot-in-practice/chapter-7/">Spring Boot in Practice: 7 Developing RESTful Web services with Spring Boot</a>
 **/
@RestController
@RequestMapping(value = "/api/v1/customers/") //TODO: IMPLEMENT CUSTOM FILTER, SO GET METHODS DON'T RETURN PASSWORDS AND FIX TEST AFTERWARDS.
@ApiOperation("Customer API")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "register/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "POST a Customer", notes = "Registers a Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer registered"),
            @ApiResponse(code = 409, message = "Could not create - No Customer registered")
    })
    public Customer registerCostumer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping(value = "find-by-id/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ') and #customer.id == #id or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET a customer by id", notes = "Returns a customer as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - Customer not found")
    })
    public Optional<Customer> getCostumerById(@AuthenticationPrincipal @ApiIgnore Customer customer, @PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping(value = "find-by-username/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET a customer by username", notes = "Returns a customer as per the username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - Customer not found")
    })
    public Optional<Customer> getByUsername(@PathVariable String username) {
        return customerService.getByUsername(username);
    }

    @GetMapping(value = "find-all/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET all customers", notes = "Returns all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - No customer found")
    })
    public Iterable<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PutMapping(value = "update-details/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE') and #customer.id == #id or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "PUT a Customer", notes = "Updates Customer details")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 409, message = "Could not update - No Customer details updated")
    })
    public Customer updateCostumerDetails(@AuthenticationPrincipal @RequestBody Customer customer, @PathVariable Long id) {
        return customerService.updateCustomerDetails(id, customer);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('DELETE') and #customer.id == #id or hasAuthority('OWNER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE a customer by id", notes = "Deletes a Customer as per id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found - Customer not found")
    })
    public void deleteCostumerById(@AuthenticationPrincipal @ApiIgnore Customer customer, @PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE all customer", notes = "Deletes all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found - No customers found")
    })
    public void deleteCustomers() {
        customerService.deleteCustomers();
    }
}
