package com.teddystore.controller;

import com.teddystore.model.Costumer;
import com.teddystore.service.CostumerService;
import com.teddystore.service.CostumerServiceImp;
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
 * <font color="#85ba6a"><strong>{@code @RequestMapping}</strong></font>: Ruta base a la que se le harán peticiones a esta clase. <br> Ej: http://localhost:8080<strong>/api/v1/costumers/find-by-id/1/</strong> <br><br>
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
 *      &emsp <font color="#8bacb0"><strong>{@code #costumer.id == #id}</strong></font>: Verifica que el usuario solicitando la información le corresponda a él, evita que usuarios no autorizados accedan a la información de otros. <br><br>
 * <font color="#85ba6a"><strong>{@code @RequestBody}</strong></font>: El método espera que el JSON enviado desde el cliente sea en el formato de este objeto. <br><br>
 * <font color="#85ba6a"><strong>{@code @PathVariable}</strong></font>: Obtiene el dato ingresado en la ruta. <br>
 *      &emsp <font color="#8bacb0"><strong>{@code find-by-id/{id}/ -> find-by-id/1/}</strong></font><br><br>
 * <font color="#85ba6a"><strong>{@code @AuthenticationPrincipal}</strong></font>: Obtiene la información del usario que solicita el método, necesario para la anotación <font color="#85ba6a"><strong>{@code @PreAuthorize}</strong></font>. <br><br>
 * <strong>
 * Ruta de clases: {@link com.teddystore.model.WebAppUser WebAppUser}
 *              -> {@link Costumer}
 *              -> {@link com.teddystore.repository.CostumerRepository  CostumerRepository}
 *              -> {@link CostumerService}
 *              -> {@link CostumerServiceImp}
 *              -> {@link CostumerController}
 * </strong>
 * @see <a href="https://livebook.manning.com/book/spring-quickly/chapter-10/">Spring Start Here: 10 Implementing REST services</a>
 * @see <a href="https://livebook.manning.com/book/spring-boot-in-practice/chapter-7/">Spring Boot in Practice: 7 Developing RESTful Web services with Spring Boot</a>
 **/
@RestController
@RequestMapping(value = "/api/v1/costumers/") //TODO: IMPLEMENT CUSTOM FILTER, SO GET METHODS DON'T RETURN PASSWORDS AND FIX TEST AFTERWARDS.
@ApiOperation("Costumer API")
public class CostumerController {

    private final CostumerService costumerService;

    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping(value = "register/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "POST a Costumer", notes = "Registers a Costumer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Costumer registered"),
            @ApiResponse(code = 409, message = "Could not create - No Costumer registered")
    })
    public Costumer registerCostumer(@RequestBody Costumer costumer) {
        return costumerService.registerCostumer(costumer);
    }

    @GetMapping(value = "find-by-id/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ') and #costumer.id == #id or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET a costumer by id", notes = "Returns a costumer as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The Costumer was not found")
    })
    public Optional<Costumer> getCostumerById(@AuthenticationPrincipal @ApiIgnore Costumer costumer, @PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping(value = "find-by-username/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET a costumer by username", notes = "Returns a costumer as per the username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The Costumer was not found")
    })
    public Optional<Costumer> getByUsername(@PathVariable String username) {
        return costumerService.getByUsername(username);
    }

    @GetMapping(value = "find-all/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    @ApiOperation(value = "GET all costumers", notes = "Returns all costumers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - No costumers registered")
    })
    public Iterable<Costumer> getAllCostumers() {
        return costumerService.getCostumers();
    }

    @PutMapping(value = "update-details/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE') and #costumer.id == #id or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "PUT a Costumer", notes = "Updates Costumer details")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 409, message = "Could not update - No Costumer details were updated")
    })
    public Costumer updateCostumerDetails(@AuthenticationPrincipal @RequestBody Costumer costumer, @PathVariable Long id) {
        return costumerService.updateCostumerDetails(id, costumer);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('DELETE') and #costumer.id == #id or hasAuthority('OWNER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE a costumer by id", notes = "Deletes a Costumer as per id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found - The Costumer was not found")
    })
    void deleteCostumerById(@AuthenticationPrincipal @ApiIgnore Costumer costumer, @PathVariable Long id) {
        costumerService.deleteCostumerById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE all costumer", notes = "Deletes all costumers")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found - No costumers were found")
    })
    void deleteCostumers() {
        costumerService.deleteCostumers();
    }
}
