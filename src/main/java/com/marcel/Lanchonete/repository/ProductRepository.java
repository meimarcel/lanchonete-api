package com.marcel.Lanchonete.repository;

import com.marcel.Lanchonete.enums.ProductType;
import com.marcel.Lanchonete.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{
    Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    Page<Product> findByType(ProductType type, Pageable pageable);
    Page<Product> findByNameIgnoreCaseContainingAndType(String name, ProductType type, Pageable pageable);
}
