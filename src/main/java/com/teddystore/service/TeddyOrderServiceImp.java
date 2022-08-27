package com.teddystore.service;

import com.teddystore.exception.OrderNotFoundException;
import com.teddystore.model.TeddyOrder;
import com.teddystore.repository.TeddyOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeddyOrderServiceImp implements TeddyOrderService{

    private final TeddyOrderRepository teddyOrderRepository;

    public TeddyOrderServiceImp(TeddyOrderRepository teddyOrderRepository) {
        this.teddyOrderRepository = teddyOrderRepository;
    }

    @Override
    public void createOrder(TeddyOrder teddyOrder) {
        teddyOrderRepository.save(teddyOrder);
    }

    @Override
    public Optional<TeddyOrder> getOrderById(Long id) {
        return Optional.ofNullable(teddyOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("No order with id %s found", id))));
    }

    @Override
    public Optional<List<TeddyOrder>> getAllOrders() {
        return Optional.empty();
    }

    @Override
    public void updateOrderDetails(Long id, TeddyOrder teddyOrder) {

    }

    @Override
    public void deleteOrderById(Long id) {

    }
}
