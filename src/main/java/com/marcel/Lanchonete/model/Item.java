package com.marcel.Lanchonete.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Item extends ModelMaster {
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @NotNull
    private String productNameInfo;

    @NotNull
    @Min(value = 1)
    private Integer quantity;

    public Item() {

    }

    public Item(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        if(product != null) {
            this.productNameInfo = product.getName();
        }
        this.quantity = quantity;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if(product != null) {
            this.productNameInfo = product.getName();
        }
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public String getProductNameInfo() {
        return this.productNameInfo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
