package com.teddystore.service;

import com.teddystore.exception.CostumerNotFoundException;
import com.teddystore.model.Costumer;
import com.teddystore.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CostumerServiceImp implements CostumerService {

    private final CostumerRepository costumerRepository;

    @Autowired
    public CostumerServiceImp(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    @Override
    public Costumer registerCostumer(Costumer costumer) {
        return costumerRepository.save(costumer);
    }

    @Override
    public Optional<Costumer> getCostumerById(Long id) {
        return Optional.ofNullable(costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id))));
    }

    @Override
    public Optional<Costumer> getByUsername(String username) {
        return costumerRepository.getByUsername(username);
    }

    @Override
    public Iterable<Costumer> getCostumers() {
        return costumerRepository.findAll();
    }

    @Override
    public Costumer updateCostumerDetails(Long id, Costumer newCostumerDetails) {
        Costumer existingCostumer = costumerRepository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException(String.format("No costumer with id %s found", id)));

        existingCostumer.updateCostumerDetails(newCostumerDetails);

        return costumerRepository.save(existingCostumer);
    }

    @Override
    public void deleteCostumerById(Long id) {
        costumerRepository.deleteById(id);
    }

    @Override
    public void deleteCostumers() {
        costumerRepository.deleteAll();
    }
}
