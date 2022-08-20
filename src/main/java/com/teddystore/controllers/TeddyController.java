package com.teddystore.controllers;

import com.teddystore.model.Teddy;
import com.teddystore.services.TeddyService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teddys/")
public class TeddyController {

    private final TeddyService teddyService;

    public TeddyController(TeddyService teddyService) {
        this.teddyService = teddyService;
    }

    @PostMapping
    public Teddy saveTeddy(@RequestBody Teddy teddy) {
        return teddyService.saveTeddy(teddy);
    }

    @GetMapping("{id}")
    public Optional<Teddy> getBYId(@PathVariable Long id) {
        return teddyService.getById(id);
    }

    @GetMapping("{name}")
    public Optional<Teddy> getByName(@PathVariable String name) {
        return teddyService.getByName(name);
    }

    @GetMapping
    public Optional<Iterable<Teddy>> getAllTeddies() {
       return teddyService.getAllTeddies();
    }

    @GetMapping("{id}")
    public Teddy updateTeddyDetails(@PathVariable Long id, @RequestBody Teddy teddy) {
        return teddyService.updateTeddyDetails(id, teddy);
    }

    @DeleteMapping
    public void deleteTeddyById(Long id) {
        teddyService.deleteTeddyById(id);
    }

    @DeleteMapping
    public void deleteAllTeddies() {
        teddyService.deleteAll();
    }
}
