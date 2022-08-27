package com.teddystore.repository;

import com.teddystore.model.TeddyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeddyOrderRepository extends CrudRepository<TeddyOrder, Long> {
}
