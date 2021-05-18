package com.marcel.Lanchonete.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ManagerUpdateDTO {
    @NotEmpty(message="Email não pode estar vazio")
    @NotNull(message="Campo 'email' não encontrado")
    @Email(message="Email inválido")
    private String email;
    @NotEmpty(message="Nome do estabelecimento não pode estar em branco")
    @NotNull(message="Campo 'companyName' não encontrado")
    private String companyName;

    public ManagerUpdateDTO() {

    }

    public ManagerUpdateDTO(String email, String companyName) {
        this.email = email;
        this.companyName = companyName;
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

}
