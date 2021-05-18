package com.marcel.Lanchonete.config;

import com.marcel.Lanchonete.dto.ManagerDTO;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.repository.ManagerRepository;
import com.marcel.Lanchonete.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private ManagerRepository managerDAO;

    @Autowired
    private Utils utils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = ((ManagerDTO) authentication.getPrincipal()).getEmail();
        String password = ((ManagerDTO)authentication.getPrincipal()).getPassword();

        Manager manager = managerDAO.findById(email)
            .orElseThrow(() -> new BadCredentialsException("Email ou Senha incorretos"));

        if(!utils.matchPassword(password, manager.getPassword())) {
            throw new BadCredentialsException("Email ou Senha incorretos");
        }
        return new UsernamePasswordAuthenticationToken(manager.getEmail(), null, manager.getAuthorities());
    }
    
}
