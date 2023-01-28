package com.teddystore.service;

import com.teddystore.model.Product;

import java.util.Optional;

public interface ProductService {

    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    Optional<Product> getProductByName(String name);

    Optional<Iterable<Product>> getAllProducts();

    Product updateProductDetails(Long id, Product product);

    void deleteProductById(Long id);

    void deleteAllProducts();
}
