package com.teddystore.repository;

import java.util.Optional;
import com.teddystore.model.TeddyOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeddyOrderRepository extends CrudRepository<TeddyOrder, Long> {

    @Query( value = "SELECT * FROM TEDDY_ORDER WHERE ID = :id " +
                    "AND CUSTOMER_ID = :customerId",
            nativeQuery = true
    )
    Optional<TeddyOrder> findTeddyOrderByIdAndCustomerId(Long id, Long customerId);
}
