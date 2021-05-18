package com.marcel.Lanchonete.helper;

import com.marcel.Lanchonete.dto.ManagerCreationDTO;
import com.marcel.Lanchonete.model.Manager;
import com.marcel.Lanchonete.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerHelper {

    @Autowired
    private Utils utils;

    public Manager toManager(ManagerCreationDTO managerCreationDTO) {
        Manager manager = new Manager();
        manager.setEmail(managerCreationDTO.getEmail());
        manager.setCompanyName(managerCreationDTO.getCompanyName());
        manager.setPassword(utils.encodePassword(managerCreationDTO.getPassword()));
        return manager;
    }

    public Manager toManager(Manager manager, ManagerCreationDTO managerCreationDTO) {
        manager.setEmail(managerCreationDTO.getEmail());
        manager.setCompanyName(managerCreationDTO.getCompanyName());
        manager.setPassword(utils.encodePassword(managerCreationDTO.getPassword()));
        return manager;
    }
}
