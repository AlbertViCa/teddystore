package com.teddystore.service;

import com.teddystore.exception.TeddyNotFoundException;
import com.teddystore.model.Product;
import com.teddystore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with id %s found", id))));
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return Optional.ofNullable(productRepository.findByName(name)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with name %s found", name))));
    }

    @Override
    public Optional<Iterable<Product>> getAllProducts() {
        return Optional.ofNullable(Optional.of(productRepository.findAll())
                .orElseThrow(() -> new TeddyNotFoundException("No teddies found")));
    }

    @Override
    public Product updateProductDetails(Long id, Product newProductDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new TeddyNotFoundException(String.format("No teddy with id %s found", id)));

        existingProduct.updateTeddyDetails(newProductDetails);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        if(teddyExists(id)) productRepository.deleteById(id);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    private boolean teddyExists(Long id) {
        if(productRepository.existsById(id)) {
            return true;
        } else {
            throw new TeddyNotFoundException(String.format("No teddy with id %s found", id));
        }
    }
}
