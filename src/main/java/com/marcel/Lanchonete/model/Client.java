package com.marcel.Lanchonete.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Client extends UserMaster {
    @NotEmpty(message = "Nome não pode estar vazio")
    @NotNull(message = "Campo 'name' não encontrado.")
    private String name;
    
    @NotNull(message = "Data de nascimento não pode estar vazio")
    private Date birthDate;
    
    @NotEmpty(message = "Número de telefone não pode estar vazio")
    @NotNull(message = "Campo 'telephoneNumber' não encontrado.")
    private String telephoneNumber;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="client")
    private List<Order> orders;

    public Client() {

    }

    public Client(String email, String name, Date birthDate, String telephoneNumber, List<Order> orders) {
        this.setEmail(email);
        this.name = name; 
        this.birthDate = birthDate;
        this.telephoneNumber = telephoneNumber;
        this.orders = orders;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
}
