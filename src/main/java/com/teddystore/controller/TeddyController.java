package com.teddystore.controller;

import com.teddystore.model.Teddy;
import com.teddystore.service.TeddyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teddies/")
public class TeddyController {

    private final TeddyService teddyService;

    @Autowired
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

    @PutMapping("{id}")
    public Teddy updateTeddyDetails(@PathVariable Long id, @RequestBody Teddy teddy) {
        return teddyService.updateTeddyDetails(id, teddy);
    }

    @DeleteMapping("{id}")
    public void deleteTeddyById(@PathVariable Long id) {
        teddyService.deleteTeddyById(id);
    }

    @DeleteMapping
    public void deleteAllTeddies() {
        teddyService.deleteAll();
    }
}
