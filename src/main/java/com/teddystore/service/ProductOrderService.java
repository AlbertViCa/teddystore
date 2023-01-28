package com.teddystore.service;

import com.teddystore.model.ProductOrder;

import java.util.Optional;

public interface ProductOrderService {

    ProductOrder createOrder(ProductOrder productOrder);

    Optional<ProductOrder> getOrderById(Long id);

    Optional<ProductOrder> findProductOrderByIdAndCustomerId(Long id, Long customerId);

    Optional<Iterable<ProductOrder>> getAllOrders();

    ProductOrder updateOrderDetails(Long id, ProductOrder productOrder);

    void deleteOrderById(Long id);

    //TODO: Analizar casos especiales: orden realizada, orden tomada y orden completada para los métodos update y delete
}
