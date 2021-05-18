package com.marcel.Lanchonete.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDTO {
    private Long id;

    @NotEmpty(message="Nome do produto não pode estar vazio")
    @NotNull(message="Campo 'name' não encontrado")
    private String name;

    @NotNull(message="Campo 'description' não encontrado")
    private String description;

    @NotEmpty(message="Tipo do produto não pode estar vazio")
    @NotNull(message="Campo 'type' não encontrado")
    private String type;

    @NotNull(message="Preço não pode estar vazio")
    @Min(value = 0L, message = "O preço não pode ser negativo")
    private Double price;

    @NotNull(message="Informe se o produto está disponível")
    private Boolean available;

    public ProductDTO() {

    }

    public ProductDTO(Long id, String name, String description, String type, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    
}
