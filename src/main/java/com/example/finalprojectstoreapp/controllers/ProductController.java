package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //CREATE
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto creatProduct(@RequestBody ProductDto productDto) {
        System.out.println(productDto);
        return productService.createProduct(productDto);
    }

    //GET ALL
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    //UPDATE
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) throws Exception {
        productDto.setId(productId);
        return productService.updateProduct(productDto);
    }

    //DELETE
    @DeleteMapping(value = "/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}