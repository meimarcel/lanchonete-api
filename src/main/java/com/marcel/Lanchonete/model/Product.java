package com.marcel.Lanchonete.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcel.Lanchonete.enums.ProductType;

@Entity
public class Product extends ModelMaster{
    @NotEmpty(message="Nome do produto não pode estar vazio")
    @NotNull(message="Campo 'name' não encontrado")
    private String name;

    @NotNull(message="Campo 'description' não encontrado")
    private String description;

    @NotNull(message="Tipo do produto não pode estar vazio")
    private ProductType type;

    @NotNull(message="Preço não pode estar vazio")
    @Min(value = 0L, message = "O preço não pode ser negativo")
    private Double price;

    @OneToMany(fetch=FetchType.EAGER, orphanRemoval = false, mappedBy="product")
    private List<Item> items;

    @NotNull(message="Informe se o produto está disponível")
    private Boolean available = true;

    public Product() {

    }

    public Product(String name, String description, ProductType type, double price, boolean available) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ProductType.parser(type);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
