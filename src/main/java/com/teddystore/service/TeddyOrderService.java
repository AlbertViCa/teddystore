package com.teddystore.service;

import com.teddystore.model.TeddyOrder;
import com.teddystore.repository.TeddyOrderRepository;

import java.util.List;
import java.util.Optional;

public interface TeddyOrderService {

    void createOrder(TeddyOrder teddyOrder);

    Optional<TeddyOrder> getOrderById(Long id);

    Optional<List<TeddyOrder>> getAllOrders();

    void updateOrderDetails(Long id, TeddyOrder teddyOrder);

    void deleteOrderById(Long id);
}
