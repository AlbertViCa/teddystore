package com.teddystore.service;

import com.teddystore.model.Costumer;

import java.util.Optional;

public interface CostumerService {

    Costumer registerCostumer(Costumer costumer);

    Optional<Costumer> getCostumerById(Long id);

    Optional<Costumer> getByUsername(String username);

    Iterable<Costumer> getCostumers();

    Costumer updateCostumerDetails(Long id, Costumer costumer);

    void deleteCostumerById(Long id);

    void deleteCostumers();
}
