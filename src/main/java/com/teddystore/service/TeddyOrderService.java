package com.teddystore.service;

import com.teddystore.model.TeddyOrder;

import java.util.Optional;

public interface TeddyOrderService {

    TeddyOrder createOrder(TeddyOrder teddyOrder);

    Optional<TeddyOrder> getOrderById(Long id);

    Optional<Iterable<TeddyOrder>> getAllOrders();

    TeddyOrder updateOrderDetails(Long id, TeddyOrder teddyOrder);

    void deleteOrderById(Long id);
}
