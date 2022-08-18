package com.teddystore.repositories;

import com.teddystore.model.Teddy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeddyRepository extends CrudRepository<Teddy, Long> {

    Optional<Teddy> findByName(String name);
}
