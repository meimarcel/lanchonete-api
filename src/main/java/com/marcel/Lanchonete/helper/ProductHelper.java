package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.ProductDTO;
import com.marcel.Lanchonete.model.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductHelper {
    public Product toProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setType(productDTO.getType());
        product.setPrice(productDTO.getPrice());
        product.setAvailable(productDTO.getAvailable());
        return product;
    }

    public Product toProduct(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setType(productDTO.getType());
        product.setPrice(productDTO.getPrice());
        product.setAvailable(productDTO.getAvailable());
        return product;
    } 
}
