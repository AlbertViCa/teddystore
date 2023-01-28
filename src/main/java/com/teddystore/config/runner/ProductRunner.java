package com.teddystore.config.runner;

import com.teddystore.model.Product;
import com.teddystore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@Order(3)
public class ProductRunner implements CommandLineRunner {

    private final ProductService productService;

    @Autowired
    public ProductRunner(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("---------- CREATING TEDDY ----------");

        Product product = Product.builder()
                .name("Bard")
                .details("Snow Fest Bard")
                .size(15.00)
                .price(BigDecimal.valueOf(300.00))
                .imageURL("image.com")
                .category(Product.Category.TEDDY)
                .build();

        log.info("---------- SAVING TEDDY ----------");

        productService.saveProduct(product);
    }
}
