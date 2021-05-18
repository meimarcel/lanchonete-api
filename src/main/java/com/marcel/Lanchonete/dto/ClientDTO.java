package com.marcel.Lanchonete.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClientDTO {
    @Email(message="Email inválido")
    @NotEmpty(message="Email não pode estar vazio")
    @NotNull(message = "Campo 'email' não encontrado")
    private String email;
    @NotEmpty(message = "Nome não pode estar vazio")
    @NotNull(message = "Campo 'name' não encontrado")
    private String name;
    @NotNull(message = "Data de nascimento não pode estar vazio")
    private Date birthDate;
    @NotEmpty(message = "Número de telefone não pode estar vazio")
    @NotNull(message = "Campo 'telephoneNumber' não encontrado")
    private String telephoneNumber;

    public ClientDTO() {

    }

    public ClientDTO(String email, String name, Date birthDate, String telephoneNumber) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    
}
