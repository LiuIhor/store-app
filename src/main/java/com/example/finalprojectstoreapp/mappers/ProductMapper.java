package com.example.finalprojectstoreapp.mappers;

import com.example.finalprojectstoreapp.dtos.ProductDto;
import com.example.finalprojectstoreapp.models.Product;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class ProductMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Product convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    public ProductDto convertToDTO(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
