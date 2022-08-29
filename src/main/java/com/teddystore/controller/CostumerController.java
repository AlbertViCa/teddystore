package com.teddystore.controller;

import com.teddystore.model.Costumer;
import com.teddystore.service.CostumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/costumers/")
public class CostumerController {

    private final CostumerService costumerService;

    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping(value = "register/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Costumer registerUser(@RequestBody Costumer costumer) {
        return costumerService.registerCostumer(costumer);
    }

    @GetMapping(value = "find-all/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Iterable<Costumer> getAllCostumers() {
        return costumerService.getCostumers();
    }

    @GetMapping(value = "find-by-id/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_costumer:read') and #costumer.id == #id or hasAuthority('SCOPE_admin')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getCostumerById(@AuthenticationPrincipal Costumer costumer, @PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping(value = "find-by-username/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getByUsername(@PathVariable String username) {
        return costumerService.getByUsername(username);
    }

    @PutMapping(value = "update-details/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_costumer:update') and #costumer.id == #id or hasAuthority('SCOPE_admin')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Costumer updateCostumerDetails(@AuthenticationPrincipal @RequestBody Costumer costumer, @PathVariable Long id) {
        return costumerService.updateCostumerDetails(id, costumer);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:delete') and #costumer.id == #id")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCostumerById(@AuthenticationPrincipal Costumer costumer, @PathVariable Long id) {
        costumerService.deleteCostumerById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('SCOPE_owner')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCostumers() {
        costumerService.deleteCostumers();
    }
}
