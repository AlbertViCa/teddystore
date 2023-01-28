package com.teddystore.repository;

import java.util.Optional;
import com.teddystore.model.ProductOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {

    @Query( value = "SELECT * FROM PRODUCT_ORDER WHERE ID = :id " +
                    "AND CUSTOMER_ID = :customerId",
            nativeQuery = true
    )
    Optional<ProductOrder> findProductOrderByIdAndCustomerId(Long id, Long customerId);
}
