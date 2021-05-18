package com.marcel.Lanchonete.service;

import com.marcel.Lanchonete.error.UserNotFoundException;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.repository.ManagerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ManagerDetailService implements UserDetailsService {

    @Autowired
    private ManagerRepository managerDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Manager manager = managerDAO.findById(email)
            .orElseThrow(() -> new UserNotFoundException("Gestor não encontrado."));
        return manager;
    }
    
}
