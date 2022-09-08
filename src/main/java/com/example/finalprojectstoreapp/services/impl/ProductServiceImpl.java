package com.example.finalprojectstoreapp.services.impl;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.exceptions.ProductNotExistException;
import com.example.finalprojectstoreapp.mappers.ProductMapper;
import com.example.finalprojectstoreapp.models.Product;
import com.example.finalprojectstoreapp.repositories.ProductRepository;
import com.example.finalprojectstoreapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.convertToEntity(productDto);
        log.info("Created product: {}", product);
        return ProductMapper.convertToDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Got list of products...");
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) throws CustomException {
        if (!productRepository.existsById(productDto.getId())) {
            log.info("CustomException. Product not present!");
            throw new CustomException("Product not present!");
        }
        Product product = ProductMapper.convertToEntity(productDto);
        log.info("Update product...");
        return ProductMapper
                .convertToDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info("Product with id {} deleted", productId);
        productRepository.deleteById(productId);
    }

    @Override
    public Product findById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.info("ProductNotExistException. Product with id {} not found", productId);
            throw new ProductNotExistException("Product is invalid: " + productId);
        }
        log.info("Find product with id {}", productId);
        return product.get();
    }

    @Override
    public ProductDto changeAvailable(Long productId, Integer quantity, boolean decreaseOrIncrease) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotExistException("Product is invalid: " + productId);
        }
        int newAvailable;
        if (decreaseOrIncrease) {
            newAvailable = product.get().getAvailable() + quantity;
        } else {
            newAvailable = product.get().getAvailable() - quantity;
        }
        product.get().setAvailable(newAvailable);
        return ProductMapper.convertToDTO(productRepository.save(product.get()));
    }
}