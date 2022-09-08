package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productServiceImpl;

    /**
     * Endpoint to create product
     *
     * @param productDto - New product
     * @return Created product
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ProductDto creatProduct(@RequestBody ProductDto productDto) {
        log.info("Will be added new product {}", productDto);
        return productServiceImpl.createProduct(productDto);
    }

    /**
     * Endpoint to get all products
     *
     * @return All products
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProducts() {
        log.info("Will be got all products");
        return productServiceImpl.getAllProducts();
    }

    /**
     * Endpoints to update product
     *
     * @param productId  - Product with this id will be updated
     * @param productDto - Product will be updated to this
     * @return Updated product
     * @throws CustomException If not found product by id
     */
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ProductDto updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto)
            throws CustomException {
        productDto.setId(productId);
        log.info("Product with id {} will be changed", productId);
        return productServiceImpl.updateProduct(productDto);
    }

    /**
     * Endpoint to delete product
     *
     * @param productId Product with this id will be deleted
     */
    @DeleteMapping(value = "/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long productId) {
        log.info("Product with id {} will be deleted", productId);
        productServiceImpl.deleteProduct(productId);
    }
}