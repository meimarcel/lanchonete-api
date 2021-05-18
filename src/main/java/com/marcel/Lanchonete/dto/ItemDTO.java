package com.marcel.Lanchonete.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ItemDTO {
    
    @NotEmpty(message="Id do produto não pode estar vazio")
    @NotNull(message="Campo 'product' não encontrado")
    private Long product;

    @NotNull(message="Quantidade do produto não pode estar vazio")
    @Min(value = 1, message="Mínimo da quantidade deve ser 1")
    private Integer quantity;

    public ItemDTO() {

    }

    public ItemDTO(Long product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
