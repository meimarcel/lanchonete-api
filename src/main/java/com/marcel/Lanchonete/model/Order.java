package com.marcel.Lanchonete.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcel.Lanchonete.enums.OrderStatus;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="order_cart")
public class Order extends ModelMaster {
    @ManyToOne
    private Client client;

    @NotNull    
    private String clientEmailInfo;
    
    @NotNull
    private String identification;

    @NotNull
    private OrderStatus status = OrderStatus.PENDENTE;

    @NotNull
    private Double totalPrice;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="order")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private List<Item> items;

    public Order() {

    }

    public Order(Client client, String identification,
            OrderStatus status, Double totalPrice, List<Item> items) {
        this.client = client;
        if(client != null) {
            this.clientEmailInfo = client.getEmail();
        }
        this.identification = identification;
        this.status = status;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if(client != null) {
            this.clientEmailInfo = client.getEmail();
        }
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public String getClientEmailInfo() {
        return clientEmailInfo;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.totalPrice = 0.0;
        for (Item item: this.items) {
            this.totalPrice += (item.getProduct().getPrice() * item.getQuantity());
        }
    }

}
