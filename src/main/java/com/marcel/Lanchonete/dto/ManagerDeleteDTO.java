package com.marcel.Lanchonete.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ManagerDeleteDTO {

    @NotEmpty(message="Senha não pode estar em branco")
    @NotNull(message="Campo 'password' não encontrado")
    private String password;
    
    public ManagerDeleteDTO() {

    }

    public ManagerDeleteDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
