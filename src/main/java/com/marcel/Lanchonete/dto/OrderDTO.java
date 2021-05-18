package com.marcel.Lanchonete.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderDTO {
    @NotEmpty(message="Email do cliente não pode estar vazio")
    @NotNull(message="Campo 'cliente' não encontrado")
    private String client;

    @NotEmpty(message="Pedido sem item")
    @NotNull(message="Campo 'items' não encontrado")
    private List<ItemDTO> items;

    public OrderDTO() {

    }

    public OrderDTO(String client, List<ItemDTO> items) {
        this.client = client;
        this.items = items;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
