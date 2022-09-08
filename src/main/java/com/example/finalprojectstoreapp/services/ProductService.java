package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.models.Product;

import java.util.List;

public interface ProductService {

    /**
     * Method to create product
     *
     * @param productDto This product to be created
     * @return Created product
     */
    ProductDto createProduct(ProductDto productDto);

    /**
     * Method to get all products
     *
     * @return List of products
     */
    List<ProductDto> getAllProducts();

    /**
     * Method to update product
     *
     * @param productDto New updated product
     * @return Updated product
     * @throws CustomException if product not present in DB
     */
    ProductDto updateProduct(ProductDto productDto);

    /**
     * Method to delete product
     *
     * @param productId id of the product to be deleted
     */
    void deleteProduct(Long productId);


    /**
     * Method to find product by id
     *
     * @param productId id of the product to be found
     * @return Found product
     */
    Product findById(Long productId);


    /**
     * Method to change available
     *
     * @param productId          Id of the product to be changed available
     * @param quantity           Quantity products
     * @param decreaseOrIncrease true - increases available, false - reduces available
     * @return product wuth changed available
     */
    ProductDto changeAvailable(Long productId, Integer quantity, boolean decreaseOrIncrease);
}