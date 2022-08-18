package com.example.finalprojectstoreapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer available;
}
