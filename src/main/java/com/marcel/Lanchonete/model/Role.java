package com.marcel.Lanchonete.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    @Id
    private String name;

    @ManyToMany(mappedBy="roles", fetch=FetchType.EAGER)
    private List<Manager> manager;

    public Role() {

    }
    
    public Role(String name) {
        this.name = name;
    }

    
    @Override
    public String getAuthority() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
