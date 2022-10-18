package com.teddystore.service;

import com.teddystore.exception.OrderNotFoundException;
import com.teddystore.model.TeddyOrder;
import com.teddystore.repository.TeddyOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeddyOrderServiceImp implements TeddyOrderService{

    private final TeddyOrderRepository teddyOrderRepository;

    public TeddyOrderServiceImp(TeddyOrderRepository teddyOrderRepository) {
        this.teddyOrderRepository = teddyOrderRepository;
    }

    @Override
    public TeddyOrder createOrder(TeddyOrder teddyOrder) {
        return teddyOrderRepository.save(teddyOrder);
    }

    @Override
    public Optional<TeddyOrder> getOrderById(Long id) {
        return Optional.ofNullable(teddyOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("No order with id %s found", id))));
    }

    @Override
    public Optional<Iterable<TeddyOrder>> getAllOrders() {
        return Optional.ofNullable(Optional.of(teddyOrderRepository.findAll())
                .orElseThrow(() -> new OrderNotFoundException("No orders found")));
    }

    @Override
    public TeddyOrder updateOrderDetails(Long id, TeddyOrder teddyOrder) {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {

    }
}
