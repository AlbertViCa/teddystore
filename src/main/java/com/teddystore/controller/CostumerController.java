package com.teddystore.controller;

import com.auth0.jwt.JWT;
import com.teddystore.model.Costumer;
import com.teddystore.service.CostumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Iterable<Costumer> getAllCostumers() {
        return costumerService.getCostumers();
    }

    @GetMapping(value = "find-by-id/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ') or hasAuthority('ADMIN')") //and #costumer.id == #id
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getCostumerById(@AuthenticationPrincipal Costumer costumer, @PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping(value = "find-by-username/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Costumer> getByUsername(@PathVariable String username) {
        return costumerService.getByUsername(username);
    }

    @PutMapping(value = "update-details/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('UPDATE') and #costumer.id == #id or hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Costumer updateCostumerDetails(@AuthenticationPrincipal @RequestBody Costumer costumer, @PathVariable Long id) {
        return costumerService.updateCostumerDetails(id, costumer);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('DELETE') and #costumer.id == #id")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCostumerById(@AuthenticationPrincipal Costumer costumer, @PathVariable Long id) {
        costumerService.deleteCostumerById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCostumers() {
        costumerService.deleteCostumers();
    }
}
