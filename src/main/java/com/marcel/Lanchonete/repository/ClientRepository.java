package com.marcel.Lanchonete.repository;

import com.marcel.Lanchonete.model.Client;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, String>{
    
}
