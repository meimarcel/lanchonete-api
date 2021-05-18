package com.marcel.Lanchonete.repository;

import com.marcel.Lanchonete.model.Item;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long>{
    
}
