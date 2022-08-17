package com.teddystore.controllers;

import com.teddystore.model.Costumer;
import com.teddystore.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users/")
public class CostumerController {

    private final CostumerService costumerService;

    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @GetMapping
    public Iterable<Costumer> getAllUsers() {
        return costumerService.getCostumers();
    }

    @GetMapping("{id}")
    public Optional<Costumer> getUserById(@PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping("costumer/{username}")
    public Optional<Costumer> getByUsername(@PathVariable String username) {
        return costumerService.getByUsername(username);
    }

    @PostMapping
    public Costumer registerUser(@RequestBody Costumer costumer) {
        return costumerService.registerCostumer(costumer);
    }

    @PutMapping("{id}")
    public void updateUserDetails(@RequestBody Costumer costumer, @PathVariable Long id) {
        costumerService.updateCostumerDetails(id, costumer);
    }

    @DeleteMapping("{id}")
    void deleteUserById(@PathVariable Long id) {
        costumerService.deleteCostumerById(id);
    }

    @DeleteMapping
    void deleteUsers() {
        costumerService.deleteCostumers();
    }
}
