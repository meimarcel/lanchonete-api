package com.marcel.Lanchonete.repository;

import com.marcel.Lanchonete.model.Role;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, String> {
    
}
