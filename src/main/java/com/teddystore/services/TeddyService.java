package com.teddystore.services;

import com.teddystore.model.Teddy;

import java.util.Optional;

public interface TeddyService {

    Teddy saveTeddy(Teddy teddy);

    Optional<Teddy> getById(Long id);

    Optional<Teddy> getByName(String name);

    Optional<Iterable<Teddy>> getAllTeddies();

    void deleteTeddyById(Long id);

    void deleteAll();
}
