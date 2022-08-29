package com.teddystore.controller;

import com.teddystore.model.Teddy;
import com.teddystore.service.TeddyService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/teddies/")            //FIXME: FIX METHODS SECURITY,HTTP RESPONSES, MAPPINGS AND CHANGE TESTS AFTERWARDS
public class TeddyController {

    private final TeddyService teddyService;

    public TeddyController(TeddyService teddyService) {
        this.teddyService = teddyService;
    }

    @PostMapping(value = "create/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:write')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Teddy saveTeddy(@RequestBody Teddy teddy) {
        return teddyService.saveTeddy(teddy);
    }

    @GetMapping("find-by-id/{id}/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Teddy> getBYId(@PathVariable Long id) {
        return teddyService.getById(id);
    }

    @GetMapping("find-by-name/{name}/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Teddy> getByName(@PathVariable String name) {
        return teddyService.getByName(name);
    }

    @GetMapping("find-all/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:read')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Iterable<Teddy>> getAllTeddies() {
       return teddyService.getAllTeddies();
    }

    @PutMapping("update-details/{id}/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:write')")
    public Teddy updateTeddyDetails(@PathVariable Long id, @RequestBody Teddy teddy) {
        return teddyService.updateTeddyDetails(id, teddy);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:delete')")
    public void deleteTeddyById(@PathVariable Long id) {
        teddyService.deleteTeddyById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('SCOPE_costumer:delete')")
    public void deleteAllTeddies() {
        teddyService.deleteAll();
    }
}
