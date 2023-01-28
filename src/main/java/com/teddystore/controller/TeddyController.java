package com.teddystore.controller;

import com.teddystore.model.Product;
import com.teddystore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/teddies/")            //FIXME: FIX METHODS SECURITY,HTTP RESPONSES, MAPPINGS AND CHANGE TESTS AFTERWARDS
public class TeddyController {

    private final ProductService productService;

    public TeddyController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "create/")
    @PreAuthorize("hasAuthority('WRITE')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product saveTeddy(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("find-by-id/{id}/")
    @PreAuthorize("hasAuthority('READ')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Product> getBYId(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("find-by-name/{name}/")
    @PreAuthorize("hasAuthority('READ')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Product> getByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("find-all/")
    @PreAuthorize("hasAuthority('READ')")
    @ResponseStatus(value = HttpStatus.FOUND)
    public Optional<Iterable<Product>> getAllTeddies() {
       return productService.getAllProducts();
    }

    @PutMapping("update-details/{id}/")
    @PreAuthorize("hasAuthority('WRITE')")
    public Product updateTeddyDetails(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProductDetails(id, product);
    }

    @DeleteMapping("delete-by-id/{id}/")
    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteTeddyById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @DeleteMapping("delete-all/")
    @PreAuthorize("hasAuthority('OWNER')")
    public void deleteAllTeddies() {
        productService.deleteAllProducts();
    }
}
