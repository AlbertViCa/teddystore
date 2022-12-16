package com.teddystore.service;

import com.teddystore.model.TeddyOrder;
import com.teddystore.repository.TeddyRepository;

import java.util.Optional;

public interface TeddyOrderService {

    TeddyOrder createOrder(TeddyOrder teddyOrder);

    Optional<TeddyOrder> getOrderById(Long id);

    Optional<TeddyOrder> findTeddyOrderByIdAndCustomerId(Long id, Long customerId);

    Optional<Iterable<TeddyOrder>> getAllOrders();

    TeddyOrder updateOrderDetails(Long id, TeddyOrder teddyOrder);

    void deleteOrderById(Long id);

    //TODO: Analizar casos especiales: orden realizada, orden tomada y orden completada para los métodos update y delete
}
