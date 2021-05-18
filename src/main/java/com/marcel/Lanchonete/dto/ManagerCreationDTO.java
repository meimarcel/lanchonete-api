package com.marcel.Lanchonete.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ManagerCreationDTO {
    
    @NotEmpty(message="Email não pode estar vazio")
    @NotNull(message="Campo 'email' não encontrado")
    @Email(message="Email inválido")
    private String email;
    @NotEmpty(message="Nome do estabelecimento não pode estar em branco")
    @NotNull(message="Campo 'companyName' não encontrado")
    private String companyName;
    @NotEmpty(message="Senha não pode estar em branco")
    @NotNull(message="Campo 'password' não encontrado")
    private String password;
    @NotEmpty(message="Confirme sua senha")
    @NotNull(message="Campo 'passwordConfirmation' não encontrado")
    private String passwordConfirmation;

    public ManagerCreationDTO() {

    }

    public ManagerCreationDTO(String email, String companyName, String password, String passwordConfirmation) {
        this.email = email;
        this.companyName = companyName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
