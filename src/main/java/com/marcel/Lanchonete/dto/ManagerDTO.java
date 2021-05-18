package com.marcel.Lanchonete.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ManagerDTO {
    @NotNull(message = "Campo 'email' não encontrado")
    @NotEmpty(message = "Email não pode estar vazio")
    @Email(message = "Email inválido")
    public String email;
    @NotNull(message = "Campo 'password' não encontrado")
    @NotEmpty(message = "Senha não pode estar vazia")
    public String password;

    public ManagerDTO() {

    }

    public ManagerDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
