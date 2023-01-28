package com.teddystore.service;

import com.teddystore.exception.OrderNotFoundException;
import com.teddystore.model.ProductOrder;
import com.teddystore.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductOrderServiceImp implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImp(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public ProductOrder createOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public Optional<ProductOrder> getOrderById(Long id) {
        return Optional.ofNullable(productOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("No order with id %s found", id))));
    }

    @Override
    public Optional<ProductOrder> findProductOrderByIdAndCustomerId(Long id, Long customerId) {
        return Optional.ofNullable(productOrderRepository.findProductOrderByIdAndCustomerId(id, customerId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("No order with id %s found", id))));
    }

    @Override
    public Optional<Iterable<ProductOrder>> getAllOrders() {
        return Optional.ofNullable(Optional.of(productOrderRepository.findAll())
                .orElseThrow(() -> new OrderNotFoundException("No orders found")));
    }

    @Override
    public ProductOrder updateOrderDetails(Long id, ProductOrder productOrder) {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {

    }
}
