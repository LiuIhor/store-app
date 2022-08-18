package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.exceptions.ProductNotExistException;
import com.example.finalprojectstoreapp.mappers.ProductMapper;
import com.example.finalprojectstoreapp.models.Product;
import com.example.finalprojectstoreapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.convertToEntity(productDto);
        return ProductMapper.convertToDTO(productRepository.save(product));
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDto updateProduct(ProductDto productDto) throws Exception {
        if (!productRepository.existsById(productDto.getId())) {
            throw new Exception("Product not present!");
        }
        Product product = ProductMapper.convertToEntity(productDto);
        return ProductMapper
                .convertToDTO(productRepository.save(product));
    }

    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        }
    }

    public Product findById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotExistException("Product is invalid: " + productId);
        }
        return product.get();
    }

    public ProductDto decreaseAvailable(Long productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotExistException("Product is invalid: " + productId);
        }

        int newAvailable = product.get().getAvailable() - quantity;

        if (newAvailable <= 0) {
            throw new CustomException("Product not enough:" + productId);
        }
        product.get().setAvailable(newAvailable);
        return ProductMapper.convertToDTO(productRepository.save(product.get()));
    }

    public ProductDto increaseAvailable(Long productId, Integer quantity) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotExistException("Product is invalid: " + productId);
        }

        int newAvailable = product.get().getAvailable() + quantity;

        product.get().setAvailable(newAvailable);
        return ProductMapper.convertToDTO(productRepository.save(product.get()));
    }
}
