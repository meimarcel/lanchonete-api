package com.marcel.Lanchonete.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ManagerUpdatePasswordDTO {
    
    @NotNull(message = "Campo 'currentPassword' não encontrado")
    @NotEmpty(message = "Senha atual não pode estar vazia")
    public String currentPassword;
    @NotNull(message = "Campo 'password' não encontrado")
    @NotEmpty(message = "Senha não pode estar vazia")
    public String password;
    @NotNull(message = "Campo 'passwordConfirmation' não encontrado")
    @NotEmpty(message="Confirme sua senha")
    public String passwordConfirmation;

    public ManagerUpdatePasswordDTO() {

    }

    public ManagerUpdatePasswordDTO(String currentPassword, String password, String passwordConfirmation) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    
}
