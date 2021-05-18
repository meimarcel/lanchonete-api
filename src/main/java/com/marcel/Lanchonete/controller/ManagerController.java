package com.marcel.Lanchonete.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.Valid;

import com.marcel.Lanchonete.assembler.ManagerAssembler;
import com.marcel.Lanchonete.config.CustomAuthenticationManager;
import com.marcel.Lanchonete.dto.ManagerCreationDTO;
import com.marcel.Lanchonete.dto.ManagerDTO;
import com.marcel.Lanchonete.dto.ManagerDeleteDTO;
import com.marcel.Lanchonete.dto.ManagerUpdateDTO;
import com.marcel.Lanchonete.dto.ManagerUpdatePasswordDTO;
import com.marcel.Lanchonete.enums.DetailType;
import com.marcel.Lanchonete.error.InfoException;
import com.marcel.Lanchonete.error.UserAlreadyExistException;
import com.marcel.Lanchonete.error.UserDoesNotExistException;
import com.marcel.Lanchonete.error.MasterDetails;
import com.marcel.Lanchonete.error.PasswordNotMatchingException;
import com.marcel.Lanchonete.helper.ManagerHelper;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.model.Role;
import com.marcel.Lanchonete.repository.ManagerRepository;
import com.marcel.Lanchonete.repository.RoleRepository;
import com.marcel.Lanchonete.service.ManagerDetailService;
import com.marcel.Lanchonete.util.ApplicationVariable;
import com.marcel.Lanchonete.util.JWTUtils;
import com.marcel.Lanchonete.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ManagerController {
    
    @Autowired
    private ManagerRepository managerDAO;
    
    @Autowired
    private RoleRepository roleDAO;

    @Autowired
    private ManagerHelper managerHelper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private Utils utils;

    @Autowired
    private ManagerAssembler managerAssembler;

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private ManagerDetailService managerDetailService;

    @PostMapping("public/manager/token")
    public ResponseEntity<?> getToken(@Valid @RequestBody ManagerDTO managerDTO) {
        this.verifyIfManagerExist();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(managerDTO, null));
        } catch(BadCredentialsException ex) {
            throw ex;
        }
        
        Manager manager = (Manager) managerDetailService.loadUserByUsername(managerDTO.getEmail());

        return ResponseEntity.ok(jwtUtils.generateAccessToken(manager.getEmail()));
    }

    @PostMapping("/public/manager/create")
    @Transactional
    public ResponseEntity<?> createManager(@Valid @RequestBody ManagerCreationDTO managerCreationDTO) {
        
        this.verifyIfManagerAlreadyExist();
        this.verifyIfPasswordsAreMatching(managerCreationDTO.getPassword(), managerCreationDTO.getPasswordConfirmation());
        
        Manager manager = managerHelper.toManager(managerCreationDTO);
        Role role = roleDAO.findById(ApplicationVariable.ROLE_MANAGER).orElse(null);
        if(role != null) {
            manager.setRoles(new ArrayList<>(Arrays.asList(role)));
        }
        manager = managerDAO.save(manager);
        
        return ResponseEntity.ok(jwtUtils.generateAccessToken(manager.getEmail()));
    }

    @GetMapping("/admin/manager/getManager")
    public ResponseEntity<?> getManager(Authentication authentication) {
        Manager manager = (Manager) authentication.getPrincipal();
        return ResponseEntity.ok(managerAssembler.toModel(manager));
    }

    @PutMapping("/admin/manager/update")
    @Transactional
    public ResponseEntity<?> updateManager(@Valid @RequestBody ManagerUpdateDTO managerUpdateDTO, Authentication authentication) {
        Manager manager = (Manager) authentication.getPrincipal();
        String currentEmail = manager.getEmail();
        String newEmail = managerUpdateDTO.getEmail();
        
        if(currentEmail.equals(newEmail)) {
            manager.setCompanyName(managerUpdateDTO.getCompanyName());
            manager = managerDAO.save(manager);
        } else {
            Manager newManager = new Manager(newEmail, managerUpdateDTO.getCompanyName(), manager.getPassword());
            managerDAO.delete(manager);
            if(managerDAO.count() != 0) {
                throw new InfoException("Não foi possível atualizar os dados, tente novamente mais tarde.", DetailType.ERROR);
            } 
            Role role = roleDAO.findById(ApplicationVariable.ROLE_MANAGER).orElse(null);
            newManager.setRoles(new ArrayList<>(Arrays.asList(role)));
            manager = managerDAO.save(newManager);
        }

        return ResponseEntity.ok(jwtUtils.generateAccessToken(manager.getEmail()));
    }

    @PutMapping("/admin/manager/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody ManagerUpdatePasswordDTO managerUpdatePasswordDTO, Authentication authentication) {
        Manager manager= (Manager) authentication.getPrincipal();
       
        if(!utils.matchPassword(managerUpdatePasswordDTO.getCurrentPassword(), manager.getPassword())) {
            throw new PasswordNotMatchingException("Senha atual incorreta.");
        }
 
        this.verifyIfPasswordsAreMatching(managerUpdatePasswordDTO.getPassword(), managerUpdatePasswordDTO.getPasswordConfirmation());

        String newPassword = utils.encodePassword(managerUpdatePasswordDTO.getPassword());
        manager.setPassword(newPassword);
        
        managerDAO.save(manager);

        return ResponseEntity.ok(MasterDetails.Builder
            .newBuilder()
            .title("Information")
            .message("Senha alterada com sucesso.")
            .type(DetailType.SUCCESS)
            .timestamp(LocalDateTime.now())
            .build());
    }

    @DeleteMapping("/admin/manager/delete")
    @Transactional
    public ResponseEntity<?> deleteManagerByManager(@Valid @RequestBody ManagerDeleteDTO managerDeleteDTO, Authentication authentication) {
        Manager manager = (Manager) authentication.getPrincipal();
        if(!utils.matchPassword(managerDeleteDTO.getPassword(), manager.getPassword())) {
            throw new InfoException("Senha Incorreta.", DetailType.ERROR);
        }
        managerDAO.delete(manager);
        
        return ResponseEntity.ok(MasterDetails.Builder
            .newBuilder()
            .title("Information")
            .message("Gestor deletado com sucesso.")
            .type(DetailType.SUCCESS)
            .timestamp(LocalDateTime.now())
            .build());
    }

    private void verifyIfManagerAlreadyExist() {
        if(managerDAO.count() != 0) {
            throw new UserAlreadyExistException("O sistema já possui um gestor cadastrado.");
        }
    }

    private void verifyIfManagerExist() {
        if(managerDAO.count() == 0) {
            throw new UserDoesNotExistException("O sistema não possui um gestor cadastrado.");
        }
    }

    private void verifyIfPasswordsAreMatching(String password1, String password2) {
        if(!password1.equals(password2)) {
            throw new PasswordNotMatchingException("As senhas não coincidem.");
        }
    }

}
