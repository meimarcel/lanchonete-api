package com.marcel.Lanchonete.repository;

import com.marcel.Lanchonete.model.Manager;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface ManagerRepository extends PagingAndSortingRepository<Manager, String> {
   
}