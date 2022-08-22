package com.teddystore.controller;

import com.teddystore.model.Costumer;
import com.teddystore.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/costumers/")
public class CostumerController {

    private final CostumerService costumerService;

    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping(value = "register/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_costumer:write')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Costumer registerUser(@RequestBody Costumer costumer) {
        return costumerService.registerCostumer(costumer);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Iterable<Costumer> getAllUsers() {
        return costumerService.getCostumers();
    }

    @GetMapping("id/{id}")
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getUserById(@PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping("costumer/{username}")
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getByUsername(@PathVariable String username) {
        return costumerService.getByUsername(username);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_costumer:write')")
    public Costumer updateUserDetails(@RequestBody Costumer costumer, @PathVariable Long id) {
        return costumerService.updateCostumerDetails(id, costumer);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_costumer:delete')")
    void deleteUserById(@PathVariable Long id) {
        costumerService.deleteCostumerById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('SCOPE_costumer:delete')")
    void deleteUsers() {
        costumerService.deleteCostumers();
    }
}
