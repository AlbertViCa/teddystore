package com.teddystore.repository;

import com.teddystore.model.Costumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostumerRepository extends CrudRepository<Costumer, Long> {

    Optional<Costumer> getByUsername(String username);
}
